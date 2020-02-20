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
		entities.addAll(entitiesToSpawn)
		entitiesToSpawn.clear()

		entities.forEach {
			passEntityToOtherChunkIfOutsideTheBounds(it)
			it.tick()

		}

		entities.removeAll(entitiesToRemove)
		entitiesToRemove.clear()

	}

	private fun passEntityToOtherChunkIfOutsideTheBounds(entity: Entity) {
		val entityChunk = blockPositionToChunkCoordinates(entity.position)

		//if entity isn't inside this chunk
		if (!entityChunk.epsilonEquals(chunkLocation)) {
			if(GameMap_new.chunkExists(entityChunk.identifier())) {
				println("Transfering entity $entity to $entityChunk")
				putEntityIntoNearbyChunk(entity, entityChunk)

			} else {
				println("Transfering entity $entity to $entityChunk failed - chunk does not exist")
				entity.despawn()

			}

		}

	}

	private fun putEntityIntoNearbyChunk(entity: Entity, intoChunk: Vector2) {
		GameMap_new.getChunk(intoChunk.identifier()).entities.add(entity)
		entitiesToRemove.add(entity)

	}

}