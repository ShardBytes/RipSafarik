package com.shardbytes.ripsafarik.actors

import box2dLight.ConeLight
import box2dLight.Light
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils.clamp
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.JsonReader
import com.shardbytes.ripsafarik.Settings
import com.shardbytes.ripsafarik.components.BlockCatalog
import com.shardbytes.ripsafarik.components.Entity
import com.shardbytes.ripsafarik.entity.ItemDrop
import com.shardbytes.ripsafarik.items.Gun

object GameMap {

	//TODO: optimize, do not pass string but already parsed json object
	fun loadAll(mapName: String) {
		val jsonString = Gdx.files.internal("world/${mapName}_map.json").readString()
		
		Env.load(jsonString)
		Overlay.load(jsonString)
		Entities.load(jsonString)
		Lights.load()
		
	}
	
	object Env {
		private var env: MutableList<MutableList<String>> = mutableListOf()

		fun load(jsonString: String) {
			val mapJson = JsonReader().parse(jsonString).get("env")

			env.clear()
			mapJson.forEach {
				val arr = it.asStringArray()
				env.add(arr.toMutableList())

			}

		}
		
		fun render(dt: Float, batch: SpriteBatch, playerPos: Vector2) {
			//nice loop
			//draw only the stuff inside player's render distance
			val yMin = clamp(playerPos.y.toInt() - Settings.RENDER_DISTANCE, 0, env.size - 1)
			val yMax = clamp(playerPos.y.toInt() + Settings.RENDER_DISTANCE, 0, env.size - 1)
			
			for (y in yMin..yMax) {
				val row = env[y]
				val xMin = clamp(playerPos.x.toInt() - Settings.RENDER_DISTANCE, 0, row.size - 1)
				val xMax = clamp(playerPos.x.toInt() + Settings.RENDER_DISTANCE, 0, row.size - 1)
				
				for (x in xMin..xMax) {
					val cell = row[x]
					batch.draw(TextureRegion(BlockCatalog[cell]?.texture), x - 0.5f, y - 0.5f, 0.5f, 0.5f, 1f, 1f, 1f, 1f, 0f)
					
				}
				
			}
			
		}
		
		operator fun get(x: Int, y: Int) = env[y][x]
		
	}
	
	object Overlay {
		
		private var overlay: MutableList<BlockData> = mutableListOf()
		
		data class BlockData(
				var name: String,
				var posX: Int,
				var posY: Int,
				var scale: Float,
				var rotation: Float)
		
		fun load(jsonString: String) {
			val mapJson = JsonReader().parse(jsonString).get("overlay")
			
			overlay.clear()
			mapJson.forEach { 
				overlay.add(BlockData(
						it["name"].asString(),
						it["posX"].asInt(),
						it["posY"].asInt(),
						it["scale"].asFloat(),
						it["rotation"].asFloat())
				)
				
			}
			
		}
		
		fun render(dt: Float, batch: SpriteBatch, playerPos: Vector2) {
			for (overlayBlock in overlay) {
				val texture = BlockCatalog[overlayBlock.name]?.texture

				if(isInRenderDistance(Vector2(overlayBlock.posX.toFloat(), overlayBlock.posY.toFloat()), playerPos)) {
					batch.draw(TextureRegion(texture), overlayBlock.posX - 0.5f, overlayBlock.posY - 0.5f, 0.5f, 0.5f, 1f, 1f, overlayBlock.scale, overlayBlock.scale, overlayBlock.rotation)

				}
				
			}
			
		}
		
	}

	object Entities {

		private var entities: MutableList<Entity> = mutableListOf()
		private var despawnSchedule: MutableList<Entity> = mutableListOf()

		fun load(jsonString: String) {
			val mapJson = JsonReader().parse(jsonString).get("entities")

			entities.clear()
			despawnSchedule.clear()

			spawn(ItemDrop(Gun()).apply { setPosition(-2f, -2f) })

			//TODO: LOADING ENTITIES FROM WORLD
/*
			mapJson.forEach {
				val obj = Class.forName(it["className"].asString())
				val instance = obj.newInstance()
				it.forEach {
					if(it.name != "className") {
						val field = obj.getDeclaredField(it.name)
						field.isAccessible = true
						field.set(instance, it.asString())

					}

				}

				spawn(instance as Entity)

			}
*/
		}

		fun render(dt: Float, batch: SpriteBatch, playerPos: Vector2) {
			for (entity in entities) {
				//if entity is in player's render distance
				//draw it
				if(isInRenderDistance(entity.position, playerPos)) {
					entity.render(dt, batch)

				}

			}

		}

		fun tick(dt: Float) {
			//tick all entities anywhere they are
			//time flows in this game everywhere, no chunk loading or similar crap magic

			//despawn all entities that are marked for despawn
			if(despawnSchedule.isNotEmpty()) {
				for(entity in despawnSchedule) {
					GameWorld.physics.destroyBody(entity.body)
					entities.remove(entity)

				}
				despawnSchedule.clear()

			}

			for(entity in entities) {
				entity.tick(dt)

			}

		}

		fun spawn(entity: Entity) {
			entities.add(entity)

		}

		fun despawn(entity: Entity) {
			if(!despawnSchedule.contains(entity)) {
				despawnSchedule.add(entity)

			}

		}

		/**
		 * Returns total count of all entities by default.
		 * Can accept predicate as a filter to return the number of entities matching the predicate.
		 * @param predicate Entity filter
		 * @return Number of entities found
		 */
		fun totalEntities(predicate: (Entity) -> Boolean = { true }): Int {
			return entities.count(predicate)

		}

	}

	object Lights {

		var lights = arrayListOf<Light>()

		fun load() {
			//val light = PointLight(GameWorld.lights, 128, Color.WHITE, 10f, 4f, 4f)
			val light = ConeLight(GameWorld.lights, 128, Color.WHITE, 10f, 0f, 0f, 0f, 40f)
			light.attachToBody(GameWorld.player.body)

			lights.add(light)

		}

	}

	fun isInRenderDistance(testPos: Vector2, playerPos: Vector2): Boolean {
		//if number is in range
		//nice Kotlin stuff
		if(testPos.x.toInt() in (playerPos.x.toInt() - Settings.RENDER_DISTANCE)..(playerPos.x.toInt() + Settings.RENDER_DISTANCE)) {
			if(testPos.y.toInt() in (playerPos.y.toInt() - Settings.RENDER_DISTANCE)..(playerPos.y.toInt() + Settings.RENDER_DISTANCE)) {
				return true

			}

		}
		return false

	}
	
}