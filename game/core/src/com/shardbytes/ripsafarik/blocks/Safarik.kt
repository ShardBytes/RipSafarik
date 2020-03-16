package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.world.Block
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class Safarik : Block {

	override val name = "safarik"
	override val displayName = "Šafárik 10/10 best block ever"
	@Transient
	override val texture = TextureRegion(Textures.Overlay["safarik"])
	
}