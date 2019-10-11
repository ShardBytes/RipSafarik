package com.shardbytes.ripsafarik.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.JsonReader
import ktx.assets.getAsset
import ktx.assets.load

class Textures {
	
	companion object {
		lateinit var manager: AssetManager
		
	}
	
	init {
		Env.load()
		//...
		
	}
	
	object Env {
		fun load() {
			val jsonString = Gdx.files.internal("textures/envTextures.json").readString()
			val textures = JsonReader().parse(jsonString).get("textures").asStringArray()

			for (texture in textures) {
				val path = "textures/env/$texture.png"
				manager.load<Texture>(path)

			}
			
		}

		operator fun get(texture: String) = manager.getAsset<Texture>("textures/env/$texture.png")
		
	}
	
}