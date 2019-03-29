package com.shardbytes.ripsafarik

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class SebuScreen : Screen {
	
	val batch = SpriteBatch()
	val player = Sprite(Texture("badlogic.jpg"))
	val cam = OrthographicCamera()
	
	init {
	
	}
	
	override fun render(delta: Float) {
	
	}
	
	
	override fun resize(width: Int, height: Int) {
	
	}
	
	override fun dispose() {
	
	}
	
	override fun pause() {}
	override fun resume() {}
	override fun hide() {}
	override fun show() {}
	
}