package com.shardbytes.ripsafarik

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.shardbytes.ripsafarik.assets.TexturesEnv

class MainGame : Game() {
	
	val assetManager = AssetManager()
	
	override fun create() {
		TexturesEnv.manager = assetManager
		TexturesEnv.values().forEach { it.load() }
		
		assetManager.finishLoading()
		
		setScreen(GameScreen())
		
		
	}
	
	override fun dispose() {
		super.dispose()
		assetManager.dispose()
	}
	
}
