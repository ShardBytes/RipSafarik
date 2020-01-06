package com.shardbytes.ripsafarik.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.assets.Animations
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.identifier
import com.shardbytes.ripsafarik.screens.MenuScreen
import com.shardbytes.ripsafarik.toVector

object MainGame : Game() {

	val assetManager = AssetManager()

	override fun create() {
		Textures.manager = assetManager
		Textures.loadAll() //Load all textures

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
