package com.shardbytes.ripsafarik.components.world

import com.badlogic.gdx.math.MathUtils.degRad
import com.badlogic.gdx.math.MathUtils.radDeg
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.components.IHealth
import com.shardbytes.ripsafarik.components.technical.GameObject
import com.shardbytes.ripsafarik.game.GameMap
import com.shardbytes.ripsafarik.game.GameWorld
import ktx.box2d.BodyDefinition
import ktx.box2d.body

/**
 * Represents a game object that can be located in the world as physical entity.
 */
abstract class Entity : GameObject, IHealth {

	/**
	 * Physics box2d body.
	 *
	 * Body definition becomes invalid after the body is unloaded/despawned.
	 */
	lateinit var body: Body
	var bodyInvalid = true
	open val bodyDef: BodyDefinition.() -> Unit = {}
	open val bodyType: BodyDef.BodyType = BodyDef.BodyType.StaticBody

	fun createBody() {
		bodyInvalid = false
		body = GameWorld.physics.body(bodyType, bodyDef)
		println("Body of $this created.")

	}

	private var savePosition: Vector2 = Vector2.Zero
	private var saveAngle: Float = 0f

	/**
	 * The position of the body.
	 */
	override val position: Vector2
		get() = body.position

	/**
	 * Sets the position of entity (sets its body transform).
	 */
	fun setPosition(x: Float, y: Float) {
		body.setTransform(x, y, body.angle)
	}

	/**
	 * Sets the position of entity (sets its body transform).
	 */
	fun setPosition(pos: Vector2) {
		body.setTransform(pos, body.angle)
	}

	/**
	 * The angle of body in degrees.
	 */
	var rotation: Float
		get() = body.angle * radDeg
		set(degrees) {
			body.setTransform(body.position, degrees * degRad)
		}
	
	fun despawn() {
		println("Despawning $this, current position: $position")
		if(!bodyInvalid) {
			GameWorld.physics.destroyBody(body)
			println("Body of $this destroyed.")
			bodyInvalid = true

		} else {
			throw Exception("Cannot destroy invalid body - entity $this.")

		}
		GameMap.despawn(this)
		
	}

	fun load() {
		createBody()
		setPosition(savePosition)
		rotation = saveAngle
		bodyInvalid = false

	}

	fun unload() {
		println("unload pizdec")
		savePosition = position
		saveAngle = rotation
		if(!bodyInvalid) {
			GameWorld.physics.destroyBody(body)
			println("Body of $this destroyed.")
			bodyInvalid = true

		} else {
			throw Exception("Cannot destroy invalid body - entity $this.")

		}

	}

}