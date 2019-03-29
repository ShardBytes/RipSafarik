package com.shardbytes.ripsafarik

import com.badlogic.gdx.Gdx.gl
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class SebuScreen : Screen {
	
	val batch = SpriteBatch()
	val player = Sprite(Texture("badlogic.jpg"))
	val cam = Camera(Camera.ResizeStrategy.KEEP_ZOOM, 10.0f, 10.0f)
	
	init {
		batch.projectionMatrix = cam.innerCamera!!.combined
	}
	
	override fun render(delta: Float) {
		gl.glClearColor(0f, 0f, 0f, 0f)
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