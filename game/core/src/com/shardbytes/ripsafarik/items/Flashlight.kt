package com.shardbytes.ripsafarik.items

import box2dLight.ConeLight
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.actors.GameWorld
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.IUsable
import com.shardbytes.ripsafarik.components.Item
import com.shardbytes.ripsafarik.entity.Player

class Flashlight : Item, IUsable {

    override val maxUses = 0
    override var leftUses = 0
    override var cooldown = 0.2f

    override var name = "flashlight"
    override val displayName = "Flashlight"
    override val texture = TextureRegion(Textures.Item["weapon/gunMagazine"])

    var light = ConeLight(GameWorld.lights, 128, Color.WHITE, 10f, 0f, 0f, 0f, 45f).apply {
        attachToBody(GameWorld.player.body)
        isActive = false

    }

    override fun use(player: Player) {
        light.isActive = !light.isActive

    }

}