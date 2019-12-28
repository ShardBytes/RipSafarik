package com.shardbytes.ripsafarik

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.shardbytes.ripsafarik.actors.GameMap
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.screens.MenuScreen

object MainGame : Game() {

	val assetManager = AssetManager()

	override fun create() {
		Textures.manager = assetManager
		Textures.loadAll()  //load all textures required for that level
							//animations actually load dynamically soo...

		//Load sounds and other assets if required
		assetManager.finishLoading()

		GameMap.loadAll("world")
		setScreen(MenuScreen())

	}

	override fun dispose() {
		super.dispose()
		assetManager.dispose()

	}

}
