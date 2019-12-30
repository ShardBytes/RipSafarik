package com.shardbytes.ripsafarik.components.world

import com.badlogic.gdx.math.MathUtils
import com.shardbytes.ripsafarik.game.GameWorld
import kotlin.math.max
import kotlin.math.min

object DaylightCycle {

	val dayLength = 300f //5 mins
	var currentTime = 0f

	fun tick(dt: Float) {
		//TODO: for party mode easter egg bruh, also hardbass music much :D - Hard Bass school: Opa blia
		currentTime += dt
		currentTime %= 300

		//set ambient light to reflect day/night

		//begins at 0.55f, continues to 1f, then 0.1f and at the end of the day (morning of the next day) it's back at 0.55f

		//val daylightValue = MathUtils.sin(currentTime / (dayLength / MathUtils.PI2)) * 0.45f + 0.55f
		val daylightValue = max(
				0.1f,
				min(
						1.0f,
						MathUtils.sin(currentTime / (dayLength / MathUtils.PI2)) * 0.55f + 0.50f
				)
		)

		GameWorld.lights.setAmbientLight(daylightValue, daylightValue, daylightValue, 0.5f)

	}

}