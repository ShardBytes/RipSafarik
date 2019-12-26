package com.shardbytes.ripsafarik

import com.badlogic.gdx.math.Vector2
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

/**
 * Re-maps a number from one range to another. That is, a value of fromLow would get mapped to toLow, a value of fromHigh to toHigh, values in-between to values in-between, etc.
 * Note that the "lower bounds" of either range may be larger or smaller than the "upper bounds" so the map() function may be used to reverse a range of numbers, for example:
 *
 * y = map(x, 1, 50, 50, 1);
 *
 * The function also handles negative numbers well, so that this example:
 *
 * y = map(x, 1, 50, 50, -100);
 *
 * is also valid and works well.
 *
 * @param value the number to map.
 * @param fromLow the lower bound of the value’s current range.
 * @param fromHigh the upper bound of the value’s current range.
 * @param toLow the lower bound of the value’s target range.
 * @param toHigh the upper bound of the value’s target range.
 * @return The mapped value.
 */
fun map(value: Float, fromLow: Float, fromHigh: Float, toLow: Float, toHigh: Float): Float {
	return (value - fromLow) * (toHigh - toLow) / (fromHigh - fromLow) + toLow

}

fun Vector2.inRange(start: Vector2, end: Vector2): Boolean {
	if(this.x >= start.x && this.x <= end.x) {
		if(this.y >= start.y && this.y <= end.y) {
			return true
			
		}
		
	}
	return false
	
}