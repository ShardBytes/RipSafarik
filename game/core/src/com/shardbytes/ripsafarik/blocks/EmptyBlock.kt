package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.world.Block

class EmptyBlock : Block {

	override val name = "default"
	override val displayName = "default"
	override val texture = TextureRegion(Textures.Overlay["unknown"])
	
}