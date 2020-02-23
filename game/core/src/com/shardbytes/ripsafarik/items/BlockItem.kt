package com.shardbytes.ripsafarik.items

import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.components.IUsable
import com.shardbytes.ripsafarik.components.world.Block
import com.shardbytes.ripsafarik.components.world.Item
import com.shardbytes.ripsafarik.copyAndround
import com.shardbytes.ripsafarik.entity.Player
import com.shardbytes.ripsafarik.game.GameMap
import com.shardbytes.ripsafarik.screens.GameScreen
import com.shardbytes.ripsafarik.ui.inventory.Hotbar
import kotlinx.serialization.Transient

class BlockItem(block: Block) : Item, IUsable {

	override val name: String = block.name
	override val displayName: String = block.displayName
	@Transient
	override val texture = block.texture

	override val maxUses = 0
	override var leftUses = 0
	override var cooldown = 0.2f

	override fun use(player: Player) {
		//place the block if it can be placed
		val screenCoordinates = GameScreen.camera.unproject(input.x, input.y)
		val position = Vector2(screenCoordinates.x, screenCoordinates.y).copyAndround()
		if (GameMap.getTile(position) == null) {
			GameMap.addTile(name, position)

			//subtract one block from ItemStack
			Hotbar.hotbarSlots[Hotbar.selectedSlot].itemStack = Hotbar.hotbarSlots[Hotbar.selectedSlot].itemStack?.oneLess()

		}

	}

	override fun `break`(player: Player) {
		TODO("not implemented")

	}

}