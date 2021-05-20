package com.shardbytes.ripsafarik.items

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.world.Item
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

//@Serializable
class GunMagazine: Item {

    override val name: String = "gunMagazine"
    override val displayName: String = "Magazine"
    //@Transient
    override val texture: TextureRegion = TextureRegion(Textures.Item["weapon/gunMagazine"])


}