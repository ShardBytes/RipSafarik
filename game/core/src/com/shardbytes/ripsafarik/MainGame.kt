package com.shardbytes.ripsafarik

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class MainGame : Game() {
	
	lateinit var batch: SpriteBatch
	
	override fun create() {
		batch = SpriteBatch()
		
		this.setScreen(SebuScreen())
		
	}
	
	override fun dispose() {
		batch.dispose()
		
	}
	
}
