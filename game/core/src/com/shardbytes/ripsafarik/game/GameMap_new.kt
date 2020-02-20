package com.shardbytes.ripsafarik.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.blockPositionToChunkCoordinates
import com.shardbytes.ripsafarik.blockPositionToChunkPosition
import com.shardbytes.ripsafarik.components.technical.BlockCatalog
import com.shardbytes.ripsafarik.components.world.Block
import com.shardbytes.ripsafarik.components.world.Entity
import com.shardbytes.ripsafarik.identifier
import com.shardbytes.ripsafarik.toVector
import com.shardbytes.ripsafarik.tools.SaveManager
import kotlinx.serialization.Serializable

object GameMap_new {

	lateinit var currentMapName: String

	@Serializable
	var chunks: MutableMap<Long, Chunk> = mutableMapOf() // TODO: is map needed?

	fun loadMap(map: String) {
		val file = Gdx.files.internal("$map.map")
		if (file.exists()) {
			println("file exists ayy")
			val json = SaveManager.json

			// TODO: Potential memory oof
			val jsonString = file.readString()

			val saveFile = json.parseJson(jsonString).jsonObject
			val loadedChunks = saveFile["chunks"]!!.jsonArray
			loadedChunks.forEach {
				val chunkJson = it.jsonObject
				val chunkIdentifier = chunkJson["chunkLocation"]!!.primitive.long
				val chunk = Chunk(chunkIdentifier.toVector())
				chunkJson["tiles"]!!.jsonArray.content.forEach { chunk.tiles.put(it.jsonObject["key"]!!.primitive.long, BlockCatalog.getBlock(it.jsonObject["value"]!!.primitive.content)) }

				chunks.put(chunkIdentifier, chunk)

			}

		}
		currentMapName = map

	}

	/**
	 * Get chunk from cache or create a new one, if it does not exist yet.
	 * @param id Chunk identifier
	 * @return New or cached existing chunk
	 *
	 * @see Vector2.identifier
	 */
	fun getChunk(id: Long): Chunk {
		val chunk = chunks[id]
		if (chunk != null) {
			return chunk

		} else {
			println("Creating new chunk at ${id.toVector()}")
			chunks[id] = Chunk(id.toVector())
			return getChunk(id)

		}

	}

	fun chunkExists(id: Long): Boolean {
		return chunks.contains(id)

	}

	fun spawn(entity: Entity) {
		val chunkId = blockPositionToChunkCoordinates(entity.position).identifier()
		getChunk(chunkId).entitiesToSpawn.add(entity)

	}

	/**
	 * Do NOT call this function directly, use despawn() directly on the entity
	 * you want to despawn!
	 *
	 * @see Entity.despawn
	 */
	fun despawn(entity: Entity) {
		val chunkId = blockPositionToChunkCoordinates(entity.position).identifier()
		getChunk(chunkId).entitiesToRemove.add(entity)

	}

	fun addTile(tileIdentifier: String, position: Vector2) {
		val chunkId = blockPositionToChunkCoordinates(position).identifier()
		val blockId = blockPositionToChunkPosition(position).identifier()
		getChunk(chunkId).tiles.put(blockId, BlockCatalog.getBlock(tileIdentifier))

	}

	fun removeTile(position: Vector2) {
		val chunkId = blockPositionToChunkCoordinates(position).identifier()
		val blockId = blockPositionToChunkPosition(position).identifier()
		getChunk(chunkId).tiles.remove(blockId)

	}

	fun getTile(position: Vector2): Block? {
		val chunkId = blockPositionToChunkCoordinates(position).identifier()
		val blockId = blockPositionToChunkPosition(position).identifier()
		return getChunk(chunkId).tiles[blockId]

	}

	fun tick() {
		chunks.forEach { it.value.tick() }

	}

	fun render(dt: Float, batch: SpriteBatch) {
		// render chunks in render distance
		forChunksInRenderDistance {
			getChunk(it).render(dt, batch)

		}

	}

	private fun forChunksInRenderDistance(action: (Long) -> Unit) {
		val playerChunk = blockPositionToChunkCoordinates(GameWorld.player.position)
		val chunkDistance = (Settings.CHUNKS_RENDER_DISTANCE - 1) / 2

		for (chunkY in playerChunk.y.toInt() - chunkDistance..playerChunk.y.toInt() + chunkDistance) {
			for (chunkX in playerChunk.x.toInt() - chunkDistance..playerChunk.x.toInt() + chunkDistance) {
				action(Vector2(chunkX.toFloat(), chunkY.toFloat()).identifier())

			}

		}

	}

}