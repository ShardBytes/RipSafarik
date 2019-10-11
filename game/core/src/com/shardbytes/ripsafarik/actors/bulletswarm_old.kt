package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.components.GameObject
import ktx.box2d.body

/**
 * Swarm that generates, renders and destroys bullets.
 * Program efficiently!
 */
class bulletswarm_old(private val world: gameworld_old) : GameObject {
	
	override val position: Vector2 = Vector2()
	
	val bullets = mutableListOf<Body>()
	val bulletsToDelete = mutableListOf<Body>()
	
	fun spawn(spawnPosition: Vector2, targetAngle: Float) {
		bullets += world.physics.body(BodyDef.BodyType.DynamicBody) {
			circle(radius = 0.2f) {
				density = 10f
				friction = 1f
				userData = this@bulletswarm_old
			}
			position.set(spawnPosition)
			angle = targetAngle
			linearVelocity.set(30f, 0f).setAngle(angle)
		}
	}
	
	fun destroy(body: Body) {
		bullets -= body
		bulletsToDelete += body
		println("bullet marked for destruction")
	}
	
	override fun act(dt: Float) {
		bulletsToDelete.forEach {
			bulletsToDelete -= it
			world.physics.destroyBody(it)
			// TODO: problem with logicc
		}
	}
	
	override fun render(dt: Float, batch: SpriteBatch) {
	
	}
	
	override fun dispose() {
	
	}
	
}