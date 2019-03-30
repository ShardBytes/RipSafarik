package com.shardbytes.ripsafarik

import com.badlogic.gdx.Gdx.files
import com.badlogic.gdx.Gdx.gl
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label

class PhysicsTestScreen : Screen {
	
	private val cam = Camera(Camera.ResizeStrategy.KEEP_ZOOM, 10f , 10f)
	private val batch = SpriteBatch()
	private val font = FreeTypeFontGenerator(files.internal("fonts/opensans.ttf")).generateFont(
			FreeTypeFontGenerator.FreeTypeFontParameter().apply {
				size = 13
			}
	)
	private val stage = Stage()
	
	override fun render(delta: Float) {
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		//batch.projectionMatrix.set(cam.innerCamera.combined)
		
		batch.begin()
		font.draw(batch, "XD", 10f, 10f)
		batch.end()
		
		stage.act(delta)
		stage.draw()
	}
	
	override fun resize(width: Int, height: Int) {
	}
	
	override fun dispose() {
	
	}
	
	override fun hide() {}
	override fun show() {}
	override fun pause() {}
	override fun resume() {}

}