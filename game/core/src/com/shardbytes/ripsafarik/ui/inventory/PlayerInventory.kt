package com.shardbytes.ripsafarik.ui.inventory

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.IUsable
import com.shardbytes.ripsafarik.components.world.Item
import com.shardbytes.ripsafarik.screens.GameScreen
import com.shardbytes.ripsafarik.ui.Healthbar

/**
 * Visual player inventory object. Contains rendering and stuff. Uses ItemInventory
 * to get information.
 * @see ItemInventory
 */
object PlayerInventory {

	var floatingItem: Item? = null

	var isOpened = false
	val slots = createSlots()

	private val inventoryTexture = TextureRegion(Textures.UI["inventorybackground"])

	fun render(dt: Float, batch: SpriteBatch) {
		if (isOpened) {
			drawBackground(batch)
			drawSlots(batch)

			drawFloatingItem(batch)

		}

	}

	private fun drawFloatingItem(batch: SpriteBatch) {
		val item = floatingItem
		if (item != null) {
			val mouseCoords = GameScreen.uiCamera.unproject(Gdx.input.x, Gdx.input.y)

			//item is drawn 1.1 unit big
			batch.draw(item.texture,
					mouseCoords.x - 0.5f,
					mouseCoords.y - 0.5f,
					0f,
					0f,
					1f,
					1f,
					1.1f,
					1.1f,
					0f)

			if (item is IUsable) {
				if (item.maxUses > 0) {
					batch.draw(Healthbar[item.leftUses],
							mouseCoords.x - 0.5f,
							mouseCoords.y - 0.5f,
							0f,
							0f,
							1f,
							0.05f,
							1.1f,
							1.1f,
							0f)

				}

			}

		}

	}

	private fun drawBackground(batch: SpriteBatch) {
		batch.draw(inventoryTexture,
				0f - 1.5f, //  - width/2
				0f - 1.5f, // - height/2
				1.5f,
				1.5f,
				3f,
				3f,
				1f,
				1f,
				0f)
	}

	private fun drawSlots(batch: SpriteBatch) {
		slots.forEach { it.render(batch) }

	}

	private fun createSlots(): Array<ItemSlot> {
		val slots = arrayListOf<ItemSlot>()
		for (i in -1..1) {
			for (j in -1..1) {
				slots.add(ItemSlot().apply { screenPosition = Vector2(j.toFloat(), i.toFloat()) })

			}

		}

		return slots.toArray(arrayOfNulls(9))

	}

}