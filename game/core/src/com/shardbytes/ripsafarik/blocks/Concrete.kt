package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.world.Block
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class Concrete : Block {

    override val name = "concrete"
    override val displayName = "Concrete"
    @Transient
    override val texture = TextureRegion(Textures.Env["beton"])

}