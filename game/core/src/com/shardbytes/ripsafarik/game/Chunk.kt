package com.shardbytes.ripsafarik.game

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.blockPositionToChunkCoordinates
import com.shardbytes.ripsafarik.components.world.Block
import com.shardbytes.ripsafarik.components.world.Entity
import com.shardbytes.ripsafarik.identifier
import com.shardbytes.ripsafarik.vectorXComponent
import com.shardbytes.ripsafarik.vectorYComponent

class Chunk(val chunkLocation: Vector2) {

	val chunkBlockLocation = chunkLocation.cpy().scl(16f)

	var tiles: MutableMap<Long, Block> = mutableMapOf()

	var entities: MutableList<Entity> = mutableListOf()
	var entitiesToSpawn: MutableList<Entity> = mutableListOf()
	var entitiesToRemove: MutableList<Entity> = mutableListOf()

	fun render(dt: Float, batch: SpriteBatch) {
		tiles.forEach { identifier, tile ->
			val posX = chunkBlockLocation.x + identifier.vectorXComponent()
			val posY = chunkBlockLocation.y + identifier.vectorYComponent()

			tile.render(batch, posX, posY)

		}

		entities.forEach {
			it.render(dt, batch)

		}

	}

	fun tick() {
		println("chunk tick ${this.chunkLocation}")
		entities.addAll(entitiesToSpawn)
		entitiesToSpawn.clear()

		entities.removeAll(entitiesToRemove)
		entitiesToRemove.clear()

		entities.forEach {
			passEntityToOtherChunkIfOutsideTheBounds(it)
			it.tick()

		}

	}

	fun load() {
		println("Load $chunkLocation")
		entities.removeAll(entitiesToRemove)
		entitiesToRemove.clear()

		entities.addAll(entitiesToSpawn)

		entities.forEach { it.load() }

	}

	fun unload() {
		println("Unload $chunkLocation")
		entitiesToSpawn.forEach { it.unload() }
		entities.forEach { it.unload() }
		entitiesToRemove.forEach { it.unload() }

	}

	private fun passEntityToOtherChunkIfOutsideTheBounds(entity: Entity) {
		// Only move entities with valid bodies (not in the process of despawning and actively loaded)
		if(!entity.bodyInvalid) {
			val entityChunk = blockPositionToChunkCoordinates(entity.position)

			//if entity isn't inside this chunk
			if (!entityChunk.epsilonEquals(chunkLocation)) {
				println("Transfering entity $entity to $entityChunk")
				putEntityIntoNearbyChunk(entity, entityChunk)

			}

		} else {
			println("BLYAAAAAAA.. no")

		}

	}

	private fun putEntityIntoNearbyChunk(entity: Entity, intoChunk: Vector2) {
		GameMap.getChunk(intoChunk.identifier()).entities.add(entity)
		entitiesToRemove.add(entity)

	}

}