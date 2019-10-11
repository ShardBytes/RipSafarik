package com.shardbytes.ripsafarik.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import ktx.assets.getAsset
import ktx.assets.load

// typesafe asset class
enum class TexturesEnv {
	asfalt, asfalt_ciara, beton, floor, grass, obrubnik, roof, roof_bad, stairs, stairs_bad, wall,
	runningorb, safarik;

	companion object {
		lateinit var manager: AssetManager
	}
	
	val path = "textures/env/$name.png"
	fun load() = manager.load<Texture>(path)
	operator fun invoke() = manager.getAsset<Texture>(path)
	
}