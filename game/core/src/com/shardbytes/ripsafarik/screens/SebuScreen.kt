package com.shardbytes.ripsafarik.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Gdx.gl
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.shardbytes.ripsafarik.actors.Player
import com.shardbytes.ripsafarik.actors.Camera

class SebuScreen : Screen {
	
	val batch = SpriteBatch()
	
	val walls = mutableListOf<Sprite>()
	
	val cam = Camera(Camera.ResizeStrategy.CHANGE_ZOOM, 10.0f, 10.0f)
	
	init {
		walls += Sprite(Texture("textures/badlogic.jpg")).apply {
			setPosition(-4f, -4f)
			setSize(1f, 1f)
			setOriginCenter()
		}
	}
	
	override fun render(dt: Float) {
		gl.glClearColor(0.3f, .3f, .3f ,0f)
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			cam.position.add(0f, -1f*dt)
		if (Gdx.input.isKeyPressed(Input.Keys.UP))
			cam.position.add(0f, 1f*dt)
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			cam.position.add(1f*dt, 0f)
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			cam.position.add(-1f*dt, 0f)
		cam.update()
		
		batch.begin()
		batch.projectionMatrix = cam.innerCamera.combined
		walls.forEach { it.draw(batch) }
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