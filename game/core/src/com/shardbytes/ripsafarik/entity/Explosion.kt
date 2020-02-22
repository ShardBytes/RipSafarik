package com.shardbytes.ripsafarik.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.shardbytes.ripsafarik.assets.Animations
import com.shardbytes.ripsafarik.components.world.Entity

class Explosion(private val strength: Float) : Entity() {

	override var maxHealth: Float = 1f
	override var health: Float = 1f
	override var regenSpeed: Float = 0f

	private val explosionAnimation = Animations["animatedExplosion"]
	private var elapsedTime = 0f
	private val explosionRotation = MathUtils.random(360f)

	//TODO: better animation
	//TODO: deal damage
	//TODO: make light(?)
	override fun render(dt: Float, batch: SpriteBatch) {
		batch.draw(explosionAnimation.getKeyFrame(elapsedTime), position.x - 0.5f, position.y - 0.5f, 0.5f, 0.5f, 1f, 1f, strength, strength, explosionRotation)
		elapsedTime += dt

	}

	override fun tick() {
		//6 frames @ 100ms each
		if (elapsedTime >= 0.6f) {
			despawn()

		}

	}

	override fun dispose() {}

}