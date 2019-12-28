package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.Block

class Lamp : Block {

    override val name = "lamp"
    override val displayName = "Street lamp"
    override val texture = TextureRegion(Textures.Overlay["lamp"])

}