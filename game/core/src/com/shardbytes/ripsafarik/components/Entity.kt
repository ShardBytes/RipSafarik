package com.shardbytes.ripsafarik.components

import com.badlogic.gdx.math.MathUtils.degRad
import com.badlogic.gdx.math.MathUtils.radDeg
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body

/**
 * Represents a game object that can be located in the world as physical entity.
 */
interface Entity : GameObject, IHealth {

	/**
	 * Physics box2d body.
	 */
	val body: Body

	/**
	 * Gets position of the body
	 */
	override val position
		get() = body.position

	/**
	 * Sets the position of entity (sets its body transform)
	 */
	fun setPosition(x: Float, y: Float) {
		body.setTransform(x, y, body.angle)
	}

	/**
	 * Sets the position of entity (sets its body transform)
	 */
	fun setPosition(pos: Vector2) {
		body.setTransform(pos.x, pos.y, body.angle)
	}

	/**
	 * Sets and gets the angle of body in degrees.
	 */
	var rotation: Float
		get() = body.angle * radDeg
		set(degrees) {
			body.setTransform(body.position, degrees * degRad)
		}

}