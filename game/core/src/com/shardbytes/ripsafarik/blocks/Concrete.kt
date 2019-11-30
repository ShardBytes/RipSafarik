package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.Texture
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.Block

class Concrete : Block {

    override val name: String = "concrete"
    override val displayName: String = "Concrete"
    override val texture: Texture = Textures.Env["beton"]

}