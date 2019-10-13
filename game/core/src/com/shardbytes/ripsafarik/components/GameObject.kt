package com.shardbytes.ripsafarik.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable

interface GameObject: Disposable {
	
	val position: Vector2
	fun render(dt: Float, batch: SpriteBatch)
	fun act(dt: Float)
	
}