package com.shardbytes.ripsafarik.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.shardbytes.ripsafarik.assets.Animations
import com.shardbytes.ripsafarik.assets.Skin
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.screens.MenuScreen

object MainGame : Game() {

	val assetManager = AssetManager()

	object Data {
		var loadSavedWorld = false

	}

	override fun create() {
		Textures.manager = assetManager
		Textures.loadAll() //Load all textures
		Skin.create() //Create skin for GUI elements

		//Load sounds and other assets if required
		assetManager.finishLoading()

		//Load all animations
		Animations.loadAll()

		setScreen(MenuScreen())

	}

	override fun dispose() {
		super.dispose()
		assetManager.dispose()

	}

}
