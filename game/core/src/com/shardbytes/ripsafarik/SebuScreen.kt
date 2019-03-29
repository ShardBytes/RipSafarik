package com.shardbytes.ripsafarik

import com.badlogic.gdx.Gdx.gl
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class SebuScreen : Screen {
	
	val batch = SpriteBatch()
	val player = Sprite(Texture("badlogic.jpg"))
	val cam = Camera(Camera.ResizeStrategy.CHANGE_ZOOM, 1000.0f, 1000.0f)
	
	init {
		player.setPosition(0f, 0f)
	}
	
	override fun render(delta: Float) {
		gl.glClearColor(1f, 0f, 0f ,0f)
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		cam.update()
		
		batch.begin()
		batch.projectionMatrix = cam.innerCamera.combined
		player.draw(batch)
		batch.end()
	}
	
	
	override fun resize(width: Int, height: Int) {
		cam.windowResized(width, height)
	}
	
	override fun dispose() {
	
	}
	
	override fun pause() {}
	override fun resume() {}
	override fun hide() {}
	override fun show() {}
	
}