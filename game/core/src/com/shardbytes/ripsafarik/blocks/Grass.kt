package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.Texture
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.Block

class Grass : Block {

	override val name: String = "grass"
	override val displayName: String = "Grass"
	override val texture: Texture = Textures.Env["grass"]

}