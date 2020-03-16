package com.shardbytes.ripsafarik.components.technical

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.shardbytes.ripsafarik.components.ITickable

interface GameObject: ITickable, Disposable {
	
	val position: Vector2
	fun render(dt: Float, batch: SpriteBatch)
	
}