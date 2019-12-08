package com.shardbytes.ripsafarik.items

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.Item

class GunMagazine: Item {

    override val name: String = "gunMagazine"
    override val displayName: String = "Magazine"
    override val texture: TextureRegion = TextureRegion(Textures.Item["weapon/gunMagazine"])


}