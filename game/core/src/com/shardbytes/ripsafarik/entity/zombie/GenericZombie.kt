package com.shardbytes.ripsafarik.entity.zombie

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.actors.GameMap
import com.shardbytes.ripsafarik.actors.GameWorld
import com.shardbytes.ripsafarik.components.Entity
import kotlin.math.min

abstract class GenericZombie : Entity {

	protected abstract var textureWidth: Float
	protected abstract var textureHeight: Float

	protected abstract var maxSpeed: Float
	protected abstract var followRange: Float
	abstract var knockbackForce: Float

	//Animation
	private var isWalking = false
	protected abstract val animatedMonster: Animation<TextureRegion>

	//Animation timings
	private var elapsedTime = 0f
	protected abstract var frames: Int
	protected abstract var frameTime: Int

	override fun tick(dt: Float) {
		//vecToPlayer.set(world.player.position.x - position.x, world.player.position.y - position.y).nor().setLength(80f)
		//rotation = vecToPlayer.angle()
		//body.applyForceToCenter(vecToPlayer, true)

		//if zombie is in the follow radius, follow the player (well obviously smh)
		val isInFollowRadius = isZombieInPlayerFollowRadius()
		if (isInFollowRadius) {
			val rotationVector = Vector2(GameWorld.player.position.x - position.x, GameWorld.player.position.y - position.y).nor()
			rotation = rotationVector.angle()
			body.setLinearVelocity(rotationVector.setLength(maxSpeed))

		} else {
			body.setLinearVelocity(0.0f, 0.0f)

		}

		//set animation to walking
		isWalking = isInFollowRadius

		//health regen
		health = min(maxHealth, health + regenSpeed)

	}

	override fun render(dt: Float, batch: SpriteBatch) {
		val originX = textureWidth * 0.5f
		val originY = textureHeight * 0.5f
		val originBasedPositionX = position.x - originX
		val originBasedPositionY = position.y - originY

		if (isWalking) {
			batch.draw(animatedMonster.getKeyFrame(elapsedTime), originBasedPositionX, originBasedPositionY, originX, originY, textureWidth, textureHeight, 1f, 1f, body.angle * MathUtils.radDeg - 90f)

		} else {
			batch.draw(animatedMonster.getKeyFrame(0.25f), originBasedPositionX, originBasedPositionY, originX, originY, textureWidth, textureHeight, 1f, 1f, body.angle * MathUtils.radDeg - 90f)

		}
		elapsedTime += dt
		elapsedTime %= frames * frameTime * 0.001f

	}

	override fun dispose() {}

	/**
	 * Damage the Zombie. If health goes below zero, despawn.
	 */
	fun takeDamage(amount: Float) {
		health -= amount

		if (health <= 0) {
			GameMap.Entities.despawn(this)

		}

	}

	fun isZombieInPlayerFollowRadius(): Boolean {
		return position.dst2(GameWorld.player.position) < (followRange * followRange)

	}

}