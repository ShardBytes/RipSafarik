package com.shardbytes.ripsafarik

import com.badlogic.gdx.physics.box2d.Contact
import com.shardbytes.ripsafarik.components.Entity

/**
 * Get entity of fixture contact by user data, if not found -> return null.
 */
inline fun <reified T : Entity> Contact.entity(block: (T) -> Unit): T? {
	fixtureA.userData.let {
		if (it is T) {
			block(it)
			return it
		}
	}
	
	fixtureB.userData.let {
		if (it is T) {
			block(it)
			return it
		}
	}
	
	return null
}