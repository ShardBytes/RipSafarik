package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.Block

class Concrete : Block {

    override val name = "concrete"
    override val displayName = "Concrete"
    override val texture = TextureRegion(Textures.Env["beton"])

}