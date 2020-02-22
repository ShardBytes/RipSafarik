package com.shardbytes.ripsafarik.components.world

import com.badlogic.gdx.math.MathUtils.degRad
import com.badlogic.gdx.math.MathUtils.radDeg
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.components.IHealth
import com.shardbytes.ripsafarik.components.technical.GameObject
import com.shardbytes.ripsafarik.game.GameMap_new
import com.shardbytes.ripsafarik.game.GameWorld
import ktx.box2d.BodyDefinition
import ktx.box2d.body
import sun.plugin.dom.exception.InvalidStateException

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
	private var bodyInvalid = false
	open val bodyDef: BodyDefinition.() -> Unit = {}
	open val bodyType: BodyDef.BodyType = BodyDef.BodyType.StaticBody

	fun createBody() {
		body = GameWorld.physics.body(bodyType, bodyDef)

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
		body.setTransform(pos.x, pos.y, body.angle)
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
		if(!bodyInvalid) {
			GameWorld.physics.destroyBody(body)
			bodyInvalid = true

		} else {
			throw InvalidStateException("Cannot destroy invalid body.")

		}
		GameMap_new.despawn(this)
		
	}

	fun load() {
		setPosition(savePosition)
		rotation = saveAngle
		createBody()
		bodyInvalid = false

	}

	fun unload() {
		savePosition = position
		saveAngle = rotation
		if(!bodyInvalid) {
			GameWorld.physics.destroyBody(body)
			bodyInvalid = true

		} else {
			throw InvalidStateException("Cannot destroy invalid body.")

		}

	}

}