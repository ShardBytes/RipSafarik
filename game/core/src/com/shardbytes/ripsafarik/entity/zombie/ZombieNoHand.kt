package com.shardbytes.ripsafarik.entity.zombie

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.game.GameWorld
import com.shardbytes.ripsafarik.assets.Animations
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ktx.box2d.body

@Serializable
class ZombieNoHand : GenericZombie() {

	override var textureWidth: Float = 1f
	override var textureHeight: Float = 1f

	override var maxSpeed: Float = 1f
	override var followRange: Float = 10f
	override var knockbackForce: Float = 20f

	@Transient
	override val animatedMonster: Animation<TextureRegion> = Animations["animatedMonster"]
	override var frames: Int = 4
	override var frameTime: Int = 150

	@Transient
	override val body = GameWorld.physics.body(BodyDef.BodyType.DynamicBody) {
		box(0.35f, 0.9f) {
			userData = this@ZombieNoHand

		}

	}

	override var maxHealth: Float = 100f
	override var health: Float = 100f
	override var regenSpeed: Float = 0f

}