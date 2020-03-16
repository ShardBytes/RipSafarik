package com.shardbytes.ripsafarik.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.LongMap
import com.shardbytes.ripsafarik.blockPositionToChunkCoordinates
import com.shardbytes.ripsafarik.blockPositionToChunkPosition
import com.shardbytes.ripsafarik.components.technical.BlockCatalog
import com.shardbytes.ripsafarik.components.world.Block
import com.shardbytes.ripsafarik.components.world.Entity
import com.shardbytes.ripsafarik.identifier
import com.shardbytes.ripsafarik.toVector
import com.shardbytes.ripsafarik.tools.SaveManager
import kotlinx.serialization.Serializable

object GameMap {

    private lateinit var currentMapName: String

    @Serializable
    var chunks: LongMap<Chunk> = LongMap() // TODO: is map needed?
    var tickedChunks: LongMap<Int> = LongMap(Settings.CHUNKS_RENDER_DISTANCE * Settings.CHUNKS_RENDER_DISTANCE)
    var chunkTickQueue: LongMap<Int> = LongMap(5)

    fun loadMap(map: String) {
        val file = Gdx.files.internal("$map.map")
        if (file.exists()) {
            println("file exists ayy")
            val json = SaveManager.json

            // TODO: Potential memory oof
            val jsonString = file.readString()

            val saveFile = json.parseJson(jsonString).jsonObject
            val loadedChunks = saveFile["chunks"]!!.jsonArray
            loadedChunks.forEach { jsonElement ->
                val chunkJson = jsonElement.jsonObject
                val chunkIdentifier = chunkJson["chunkLocation"]!!.primitive.long
                val chunk = Chunk(chunkIdentifier.toVector())
                //chunkJson["groundTiles"]!!.jsonArray.content.forEach { chunk.groundTiles.put(it.jsonObject["key"]!!.primitive.long, BlockCatalog.getBlock(it.jsonObject["value"]!!.primitive.content)) }

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

        chunkTickQueue.put(id, Settings.CHUNK_LOAD_TIME)

        if (chunk != null) {
            // Load the chunk first if it was unloaded (not in currently ticking chunks)
            if (!tickedChunks.containsKey(id) && !chunkTickQueue.containsKey(id)) chunks[id]?.load()
            return chunk

        } else {
            println("Creating new chunk at ${id.toVector()}")
            chunks.put(id, Chunk(id.toVector()))
            return chunks[id]!!

        }

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

    fun addTile(tileIdentifier: String, position: Vector2, zIndex: Int = Int.MAX_VALUE) {
        val chunkId = blockPositionToChunkCoordinates(position).identifier()
        val blockId = blockPositionToChunkPosition(position).identifier()

        val chunk = getChunk(chunkId)
        val keys = chunk.tiles.orderedKeys()
        if (zIndex == Int.MAX_VALUE) {
            val largestKey = largestKey(keys)
            if (largestKey != Int.MIN_VALUE) {
                // Find smallest unoccupied layer
                var smallestKey = Int.MAX_VALUE
                for (key in keys) {
                    if (key < smallestKey) {
                        if (getTile(position, smallestKey) == null) {
                            smallestKey = key

                        }

                    }

                }
                if (smallestKey != Int.MAX_VALUE) {
                    chunk.tiles[smallestKey].put(blockId, BlockCatalog.getBlock(tileIdentifier))

                } else {
                    // All layers are occupied so create a new one above the largest
                    chunk.tiles.put(largestKey + 1, LongMap(256))
                    chunk.tiles[largestKey + 1].put(blockId, BlockCatalog.getBlock(tileIdentifier))

                }

            } else {
                chunk.tiles.put(0, LongMap(256))
                chunk.tiles[0].put(blockId, BlockCatalog.getBlock(tileIdentifier))

            }

        } else {
            if (chunk.tiles.containsKey(zIndex)) {
                chunk.tiles[zIndex].put(blockId, BlockCatalog.getBlock(tileIdentifier))

            } else {
                chunk.tiles.put(zIndex, LongMap(256))
                chunk.tiles[zIndex].put(blockId, BlockCatalog.getBlock(tileIdentifier))

            }

        }
        chunk.tiles.orderedKeys().sort()

    }

    fun removeTile(position: Vector2, zIndex: Int = Int.MAX_VALUE) {
        val chunkId = blockPositionToChunkCoordinates(position).identifier()
        val blockId = blockPositionToChunkPosition(position).identifier()

        val chunk = getChunk(chunkId)
        val keys = chunk.tiles.orderedKeys()

        if (zIndex == Int.MAX_VALUE) {
            val largestKey = largestKey(keys)
            if (chunk.tiles.containsKey(largestKey)) {
                chunk.tiles[largestKey(keys)].remove(blockId)

            }

        } else {
            if (chunk.tiles.containsKey(zIndex)) {
                chunk.tiles[zIndex].remove(blockId)

            }

        }

    }

    fun getTile(position: Vector2, zIndex: Int = Int.MAX_VALUE): Block? {
        val chunkId = blockPositionToChunkCoordinates(position).identifier()
        val blockId = blockPositionToChunkPosition(position).identifier()

        val chunk = getChunk(chunkId)
        if (zIndex == Int.MAX_VALUE) {
            val largestKey = largestKey(chunk.tiles.orderedKeys())
            return chunk.tiles[largestKey][blockId]

        } else {
            return chunk.tiles[zIndex][blockId]

        }

    }

    private fun largestKey(keys: Array<Int>): Int {
        var largestKey = Int.MIN_VALUE
        for (key in keys) if (key > largestKey) largestKey = key
        return largestKey

    }

    fun tick() {
        tickedChunks.forEach {
            tickedChunks.put(it.key, --it.value)
            if (it.value < 1) {
                chunks[it.key]?.unload()

            }

        }
        tickedChunks.removeAll { it.value < 1 }

        forChunksInRenderDistance { tickedChunks.put(it, Settings.CHUNK_LOAD_TIME) }
        tickedChunks.putAll(chunkTickQueue)
        chunkTickQueue.clear()

        tickedChunks.forEach { chunks[it.key]?.tick() }

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