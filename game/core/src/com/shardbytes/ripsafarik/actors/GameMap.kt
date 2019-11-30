package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils.clamp
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.JsonReader
import com.shardbytes.ripsafarik.Settings
import com.shardbytes.ripsafarik.components.BlockCatalog
import com.shardbytes.ripsafarik.components.Entity

object GameMap {
	
	fun loadAll(mapName: String) {
		val jsonString = Gdx.files.internal("world/${mapName}_map.json").readString()
		
		Env.load(jsonString)
		Overlay.load(jsonString)
		
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

		fun load() {
			return
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
			for (entity in entities) {
				entity.tick(dt)

			}

		}

		fun spawn(entity: Entity) {
			entities.add(entity)

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