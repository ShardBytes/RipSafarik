package com.shardbytes.ripsafarik

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.screens.GameScreen

class MainGame : Game() {
	
	val assetManager = AssetManager()
	
	override fun create() { 
		Textures.manager = assetManager
		Textures()  //load all textures required for that level
					//animations actually load dynamically soo...
		
		//Load sounds and other assets if required
		assetManager.finishLoading()
		
		setScreen(GameScreen())
		
		
	}
	
	override fun dispose() {
		super.dispose()
		assetManager.dispose()
	}
	
}
