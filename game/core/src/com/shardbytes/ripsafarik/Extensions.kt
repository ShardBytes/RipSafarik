package com.shardbytes.ripsafarik

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture

/**
 * Get entity of fixture contact by user data, if not found -> return null.
 */
inline fun <reified T> Contact.dataType(block: (T, Fixture) -> Unit): T? {
	fixtureA.userData.let {
		if (it is T) {
			block(it, fixtureA)
			return it
		}
	}
	
	fixtureB.userData.let {
		if (it is T) {
			block(it, fixtureB)
			return it
		}
	}
	
	return null
}