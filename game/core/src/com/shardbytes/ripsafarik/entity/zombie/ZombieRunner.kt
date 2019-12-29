package com.shardbytes.ripsafarik.entity.zombie

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.actors.GameWorld
import com.shardbytes.ripsafarik.assets.Animations
import ktx.box2d.body

class ZombieRunner : GenericZombie() {

	override var textureWidth: Float = 1f
	override var textureHeight: Float = 1f

	override var maxSpeed: Float = 3f
	override var followRange: Float = 10f
	override var knockbackForce: Float = 50f

	override val animatedMonster: Animation<TextureRegion> = Animations["animatedFastMonster"]
	override var frames: Int = 4
	override var frameTime: Int = 100

	override val body = GameWorld.physics.body(BodyDef.BodyType.DynamicBody) {
		box(0.35f, 0.5f) {
			userData = this@ZombieRunner

		}

	}

	override var maxHealth: Float = 100f
	override var health: Float = 100f
	override var regenSpeed: Float = 0f
	
}