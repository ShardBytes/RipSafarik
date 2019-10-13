package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.Texture
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.Block

class Asfalt : Block {

	override val name: String = "asfalt"
	override val displayName: String = "Asphalt"
	override val texture: Texture = Textures.Env["asfalt"]
	
}