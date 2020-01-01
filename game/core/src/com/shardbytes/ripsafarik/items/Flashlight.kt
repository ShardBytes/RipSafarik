package com.shardbytes.ripsafarik.items

import box2dLight.ConeLight
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.game.GameWorld
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.IUsable
import com.shardbytes.ripsafarik.components.world.Item
import com.shardbytes.ripsafarik.entity.Player
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class Flashlight : Item, IUsable {

    override val maxUses = 0
    override var leftUses = 0
    override var cooldown = 0.2f

    override var name = "flashlight"
    override val displayName = "Flashlight"
    @Transient override val texture = TextureRegion(Textures.Item["weapon/gunMagazine"])

    @Transient var light = ConeLight(GameWorld.lights, 128, Color.WHITE, 10f, 0f, 0f, 0f, 45f).apply {
        attachToBody(GameWorld.player.body)
        isActive = false

    }

    override fun use(player: Player) {
        light.isActive = !light.isActive

    }

    override fun `break`(player: Player) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

}