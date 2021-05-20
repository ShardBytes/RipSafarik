package com.shardbytes.ripsafarik.items

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.IWeapon
import com.shardbytes.ripsafarik.components.world.Item
import com.shardbytes.ripsafarik.entity.Bullet
import com.shardbytes.ripsafarik.entity.Player
import com.shardbytes.ripsafarik.game.GameMap
import com.shardbytes.ripsafarik.ui.inventory.Hotbar
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

//@Serializable
class Gun : Item, IWeapon {

	override val name = "gun"
	override val displayName = "Gun"

	//@Transient
	override val texture = TextureRegion(Textures.Item["weapon/gun"])

	override val maxUses: Int = 100
	override var leftUses: Int = 87
	override var cooldown = 0.05f

	override fun use(player: Player) {
		val playerRotation = player.rotation
		val bullet = Bullet().apply {
			createBody()
			setPosition(player.position.cpy().add(Vector2.X.cpy().rotate(rotation).setLength(0.65f)))
			body.linearVelocity = Vector2.X.setLength(360f).setAngle(playerRotation)

		}
		GameMap.spawn(bullet)

		if (--leftUses == 0) {
			`break`(player)

		}

	}

	override fun `break`(player: Player) {
		//TODO: maybe a better way of doing this?
		Hotbar.hotbarSlots[Hotbar.selectedSlot].itemStack = null

	}

}