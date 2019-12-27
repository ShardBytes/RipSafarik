package com.shardbytes.ripsafarik.items

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.actors.GameMap
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.Item
import com.shardbytes.ripsafarik.components.Weapon
import com.shardbytes.ripsafarik.entity.Bullet
import com.shardbytes.ripsafarik.entity.Player
import com.shardbytes.ripsafarik.ui.inventory.Hotbar

class Gun : Item, Weapon {

    override val name = "gun"
    override val displayName = "Gun"
    override val texture = TextureRegion(Textures.Item["weapon/gun"])

    override val maxUses: Int = 100
    override var leftUses: Int = 87
    override var cooldown = 0.05f

    override fun use(player: Player) {
        val playerPos = player.position.cpy()
        val playerRotation = player.rotation
        val bullet = Bullet().apply {
            setPosition(playerPos.add(Vector2.X.rotate(rotation).setLength(0.65f)))
            body.linearVelocity = Vector2.X.setLength(30f).setAngle(playerRotation)

        }

        GameMap.Entities.spawn(bullet)
        if(--leftUses == 0) {
            `break`(player)

        }

    }

    override fun `break`(player: Player) {
        //TODO: maybe a better way of doing this?
        Hotbar.hotbarSlots[Hotbar.selectedSlot].item = null
        
    }

}