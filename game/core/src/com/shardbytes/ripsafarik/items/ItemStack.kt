package com.shardbytes.ripsafarik.items

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.IUsable
import com.shardbytes.ripsafarik.components.world.Item
import com.shardbytes.ripsafarik.ui.Healthbar
import kotlinx.serialization.Serializable

@Serializable
data class ItemStack(var item: Item, var amount: Int) {

	fun render(batch: SpriteBatch, screenPosition: Vector2, slotSize: Float) {
		batch.draw(item.texture,
				screenPosition.x - slotSize * 0.5f,
				screenPosition.y - slotSize * 0.5f,
				0.5f,
				0.5f,
				1f,
				1f,
				slotSize * 0.66f,
				slotSize * 0.66f,
				0f)

		val usableItem = item as? IUsable
		if (usableItem != null) {
			if (usableItem.maxUses != 0) {
				val usesLeft = Healthbar[(usableItem.leftUses.toFloat() / usableItem.maxUses.toFloat() * 100f).toInt()]
				batch.draw(usesLeft,
						screenPosition.x - slotSize * 0.5f,
						screenPosition.y - slotSize * 0.5f,
						0.5f,
						0.5f,
						1f,
						0.05f, // 1/20th
						slotSize * 0.66f,
						slotSize * 0.66f,
						0f)

			}

		}

		if (amount > 1) {
			Textures.Font.bitmapFont.draw(batch, amount.toString(), screenPosition.x + slotSize * 0.5f, screenPosition.y + slotSize * 0.5f)

		}

	}

	/**
	 * Compare two ItemStacks by their item's technical name.
	 * @param itemStack other ItemStack to compare to
	 * @return if two ItemStacks have the same item by its technical name
	 */
	fun hasTheSameItemAs(itemStack: ItemStack?) = item.name.equals(itemStack?.item?.name)

	/**
	 * Sets this ItemStacks' value to half and returns the other half
	 * as a new ItemStack.
	 * @return the other half of ItemStack
	 */
	fun half(): ItemStack {
		val half = amount / 2
		amount -= half
		return ItemStack(item, half)
		
	}

	/**
	 * Subtracts one item from amount counter of this ItemStack and returns
	 * the amount left (or null if there are no more items to be subtracted).
	 * @return ItemStack with the amount left, or null if there is nothing left
	 */
	fun oneLess(): ItemStack? {
		amount -= 1
		return if(amount == 0) null else this
		
	}

	/**
	 * Splits one item from this ItemStack and returns it as a new ItemStack.
	 * 1 item is subtracted from this ItemStack.
	 * @return ItemStack with 1 item split from this ItemStack
	 */
	fun splitOne(): ItemStack? {
		amount -= 1
		return if(amount == 0) null else ItemStack(item, 1)
		
	}

}