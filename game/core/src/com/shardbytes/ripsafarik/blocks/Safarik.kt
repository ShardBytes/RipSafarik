package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.Texture
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.Block

class Safarik : Block {

	override val name: String = "safarik"
	override val texture: Texture = Textures.Overlay["safarik"]
	
}