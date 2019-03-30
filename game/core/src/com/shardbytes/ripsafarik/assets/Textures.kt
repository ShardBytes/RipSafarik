package com.shardbytes.ripsafarik.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import ktx.assets.getAsset
import ktx.assets.load

// typesafe asset class
enum class TexturesEnv {
	asfalt, asfalt_ciara, beton, floor, grass, obrubnik, roof, roof_bad, stairs, stairs_bad, wall,
	runningorb;
	
	val path = "textures/env/${name}.png"
	fun load() = manager.load<Texture>(path)
	operator fun invoke() = manager.getAsset<Texture>(path)
	
	companion object {
		lateinit var manager: AssetManager
	}
}

enum class TexturesEntity {
	animatedFastMonster, animatedMonster, animatedMonster2, animatedMonster3, animatedPlayer;
	
	val path = "textures/entity/${name}.gif"
	fun load() = manager.load<Texture>(path)
	operator fun invoke() = manager.getAsset<Texture>(path)
	
	companion object {
		lateinit var manager: AssetManager
	}
}