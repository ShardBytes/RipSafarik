package com.shardbytes.ripsafarik.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.game.GameMap
import com.shardbytes.ripsafarik.game.GameWorld
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.world.Entity
import ktx.assets.disposeSafely
import ktx.box2d.body

class Bullet : Entity {

	private val texture = TextureRegion(Textures.Entity["bullet"])
	private val width = 0.1f
	private val height = 0.1f

	//seconds before despawn
	override var maxHealth: Float = 3f
	override var health: Float = 3f
	override var regenSpeed: Float = 0f

	override val body: Body = GameWorld.physics.body(BodyDef.BodyType.DynamicBody) {
		circle(0.1f) { userData = this@Bullet }
		bullet = true

	}

	override fun render(dt: Float, batch: SpriteBatch) {
		val originX = width * 0.5f
		val originY = height * 0.5f
		val originBasedPositionX = position.x - originX
		val originBasedPositionY = position.y - originY

		batch.draw(texture, originBasedPositionX, originBasedPositionY, originX, originY, width, height, 1f, 1f, body.angle * MathUtils.radDeg - 90f)

	}

	override fun tick(dt: Float) {
		health -= dt
		if (health <= 0f) {
			GameMap.Entities.despawn(this)

		}

	}

	override fun dispose() {
		//TODO: check if this has an effect or not
		texture.texture.disposeSafely()

	}

}