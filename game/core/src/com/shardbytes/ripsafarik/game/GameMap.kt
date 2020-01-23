package com.shardbytes.ripsafarik.game
/*
import box2dLight.Light
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.JsonReader
import com.shardbytes.ripsafarik.components.technical.BlockCatalog
import com.shardbytes.ripsafarik.components.world.Block
import com.shardbytes.ripsafarik.components.world.Entity
import com.shardbytes.ripsafarik.entity.Explosion
import com.shardbytes.ripsafarik.entity.ItemDrop
import com.shardbytes.ripsafarik.items.DestroyTool
import com.shardbytes.ripsafarik.items.Flashlight
import com.shardbytes.ripsafarik.items.Gun
import kotlin.math.round
import kotlin.math.roundToInt

object GameMap {

	//TODO: optimize, do not pass string but already parsed json object
	fun loadAll(mapName: String) {
		val jsonString = Gdx.files.internal("world/${mapName}_map.json").readString()

		Env.load(jsonString)
		Overlay.load(jsonString)
		Entities.load(jsonString)

	}

	object Env {
		private var env: MutableList<MutableList<Block?>> = mutableListOf()

		fun load(jsonString: String) {
			val mapJson = JsonReader().parse(jsonString).get("env")

			env.clear()
			mapJson.forEach {
				val arr = it.asStringArray()
				val arr2 = mutableListOf<Block?>()
				arr.forEachIndexed { index, s ->
					arr2.add(BlockCatalog.getBlock(s))

				}
				env.add(arr2)

			}

		}

		fun render(dt: Float, batch: SpriteBatch, playerPos: Vector2) {/*
			//nice loop
			//draw only the stuff inside player's render distance
			val yMin = clamp(playerPos.y.toInt() - Settings.RENDER_DISTANCE, 0, env.size - 1)
			val yMax = clamp(playerPos.y.toInt() + Settings.RENDER_DISTANCE, 0, env.size - 1)

			for (y in yMin..yMax) {
				val row = env[y]
				val xMin = clamp(playerPos.x.toInt() - Settings.RENDER_DISTANCE, 0, row.size - 1)
				val xMax = clamp(playerPos.x.toInt() + Settings.RENDER_DISTANCE, 0, row.size - 1)

				for (x in xMin..xMax) {
					val block = row[x]
					block?.render(batch, x.toFloat(), y.toFloat())

				}

			}*/

		}

		fun remove(coords: Vector2): Boolean {
			if ((coords.y < 0 || coords.x < 0) || (coords.y > env.size - 1 || coords.x > env[coords.y.roundToInt()].size - 1)) {
				return false

			}

			val block = env[coords.y.roundToInt()][coords.x.roundToInt()]
			block?.onDestroy(coords)

			env[coords.y.roundToInt()][coords.x.roundToInt()] = null
			return block != null

		}

		operator fun get(x: Int, y: Int) = env[y][x]

	}

	object Overlay {

		private var overlay: MutableList<BlockData> = mutableListOf()

		data class BlockData(
				var name: String,
				var posX: Float,
				var posY: Float,
				var scale: Float,
				var rotation: Float)

		fun load(jsonString: String) {
			val mapJson = JsonReader().parse(jsonString).get("overlay")

			overlay.clear()
			mapJson.forEach {
				val data = BlockData(
						it["name"].asString(),
						it["posX"].asFloat(),
						it["posY"].asFloat(),
						it["scale"].asFloat(),
						it["rotation"].asFloat()
				)
				overlay.add(data)

				val pos = Vector2(data.posX, data.posY)
				BlockCatalog.getBlock(data.name).createCollider(pos)
				BlockCatalog.getBlock(data.name).onCreate(pos)

			}

		}

		fun render(dt: Float, batch: SpriteBatch, playerPos: Vector2) {
			for (overlayBlock in overlay) {
				val block = BlockCatalog.getBlock(overlayBlock.name)

				if (isInRenderDistance(Vector2(overlayBlock.posX, overlayBlock.posY), playerPos)) {
					block.render(batch, overlayBlock.posX, overlayBlock.posY, overlayBlock.scale, overlayBlock.scale, overlayBlock.rotation)

				}

			}

		}

		fun remove(coords: Vector2): Boolean {
			val roundedCoords = Vector2(round(coords.x), round(coords.y))
			val data = overlay.find { it.posX == roundedCoords.x && it.posY == roundedCoords.y }
			if (data != null) {
				val block = BlockCatalog.getBlock(data.name)
				block.onDestroy(roundedCoords)

				overlay.remove(data)
				return true

			}
			return false

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
			spawn(ItemDrop(Flashlight()).apply { setPosition(0f, -2f) })
			spawn(ItemDrop(DestroyTool()).apply { setPosition(2f, -2f) })

			spawn(Explosion(4f).apply { setPosition(4f, 4f) })

			//TODO: LOADING ENTITIES FROM WORLD
			//this uses Java reflection, probably not the best solution there is
			//pretty sure not the best solution there is
			//NOPE NOT AT ALL
			mapJson.forEach {
				val obj = Class.forName(it["className"].asString())
				val instance = obj.newInstance()
				it.forEach {
					if (it.name != "className") {
						val field = obj.getDeclaredField(it.name)
						field.isAccessible = true
						field.set(instance, it.asFloat())

					}

				}
				spawn(instance as Entity)

			}

		}

		fun render(dt: Float, batch: SpriteBatch, playerPos: Vector2) {
			for (entity in entities) {
				//if entity is in player's render distance
				//draw it
				if (isInRenderDistance(entity.position, playerPos)) {
					entity.render(dt, batch)

				}

			}

		}

		fun tick(dt: Float) {
			//tick all entities anywhere they are
			//time flows in this game everywhere, no chunk loading or similar crap magic

			//despawn all entities that are marked for despawn
			if (despawnSchedule.isNotEmpty()) {
				for (entity in despawnSchedule) {
					GameWorld.physics.destroyBody(entity.body)
					entities.remove(entity)

				}
				despawnSchedule.clear()

			}

			for (entity in entities) {
				entity.tick()

			}

		}

		fun spawn(vararg entity: Entity) {
			entity.forEach { entities.add(it) }

		}

		fun despawn(entity: Entity) {
			if (!despawnSchedule.contains(entity)) {
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
			//val light = ConeLight(GameWorld.lights, 128, Color.WHITE, 10f, 0f, 0f, 0f, 40f)
			//light.attachToBody(GameWorld.player.body)

			//lights.add(light)

		}

		fun createNew(light: Light) {
			lights.add(light)

		}

	}

	fun isInRenderDistance(testPos: Vector2, playerPos: Vector2): Boolean {
		//if number is in range
		//nice Kotlin stuff
		/*if (testPos.x.toInt() in (playerPos.x.toInt() - Settings.RENDER_DISTANCE)..(playerPos.x.toInt() + Settings.RENDER_DISTANCE)) {
			if (testPos.y.toInt() in (playerPos.y.toInt() - Settings.RENDER_DISTANCE)..(playerPos.y.toInt() + Settings.RENDER_DISTANCE)) {
				return true

			}

		}*/
		return false

	}

}
		*/