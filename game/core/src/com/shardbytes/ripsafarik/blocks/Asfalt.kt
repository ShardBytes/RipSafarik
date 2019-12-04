package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.Block

class Asfalt : Block {

	override val name = "asfalt"
	override val displayName = "Asphalt"
	override val texture = TextureRegion(Textures.Env["asfalt"])
	
}