package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.world.Block
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class IronFence: Block {

	override val name: String = "ironFence"
	override val displayName: String = "Iron fence"
	@Transient
	override val texture: TextureRegion = TextureRegion(Textures.Overlay["iron_fence"])
	
}