package com.shardbytes.ripsafarik.game

object Settings {

	var PHYSICS_DEBUG_ACTIVE = true

	const val GAME_V_WIDTH = 15f
	const val GAME_V_HEIGHT = 15f

	var CURRENT_ASPECT_RATIO = 1f

	val VISIBLE_SCREEN_HEIGHT_GUI: Float
		get() = GAME_V_HEIGHT / CURRENT_ASPECT_RATIO

	var CHUNKS_RENDER_DISTANCE = 3 //plz nepárne čísla only
	var CHUNK_LOAD_TIME = 240 //load chunks for 4s min

}