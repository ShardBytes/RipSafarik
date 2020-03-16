package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.world.Block
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class Asfalt : Block {

	override val name = "asfalt"
	override val displayName = "Asphalt"
	@Transient
	override val texture = TextureRegion(Textures.Env["asfalt"])
	
}