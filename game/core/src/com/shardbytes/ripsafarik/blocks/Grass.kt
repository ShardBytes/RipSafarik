package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.Block

class Grass : Block {

	override val name = "grass"
	override val displayName = "Grass"
	override val texture = TextureRegion(Textures.Env["grass"])

}