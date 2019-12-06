package com.shardbytes.ripsafarik.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.Settings
import com.shardbytes.ripsafarik.actors.GameWorld
import com.shardbytes.ripsafarik.assets.Textures

object Hotbar {
	
	var hotbarSlots = 4
	var selectedSlot = 0
	
	private val itemSlotTexture = TextureRegion(Textures.UI["itemslot"])
	private val itemSlotTextureSize = 1f
	
	fun render(dt: Float, batch: SpriteBatch) {
		drawHotbarSlots(batch)
		drawSelectedSlot(batch) //Draw that one slot one more time to make it less transparent for now
								//TODO: you can do better <-- graphics i meant

		drawItems(batch)
		
	}

	private fun drawSelectedSlot(batch: SpriteBatch) {
		val slotNumber = selectedSlot - Math.round(hotbarSlots / 2f)

		batch.draw(itemSlotTexture,
				(itemSlotTextureSize * slotNumber) - (if (hotbarSlots % 2 == 0) {
					0.0f
				} else {
					0.5f
				}),
				(Settings.GAME_V_HEIGHT * -0.5f) / Settings.CURRENT_ASPECT_RATIO,
				0.5f,
				0.5f,
				1f,
				1f,
				itemSlotTextureSize,
				itemSlotTextureSize,
				0f)

	}

	private fun drawItems(batch: SpriteBatch) {
		for (i in Math.round(-(hotbarSlots / 2f)) until Math.round((hotbarSlots / 2f))) {
			val item = GameWorld.player.inventory.hotbar[i + Math.round(hotbarSlots / 2f)]

			if (item != null) {
				batch.draw(item.texture,
						(itemSlotTextureSize * i) - (if (hotbarSlots % 2 == 0) {
							0.0f
						} else {
							0.5f
						}),
						(Settings.GAME_V_HEIGHT * -0.5f) / Settings.CURRENT_ASPECT_RATIO,
						0.5f, //Rotation origin
						0.5f, //Ah i see
						1f,
						1f,
						itemSlotTextureSize - 0.33f, //Make item sligthly smaller in the inventory
						itemSlotTextureSize - 0.33f, //TODO: scale is good?
						0f)

			}

		}

	}

	private fun drawHotbarSlots(batch: SpriteBatch) {
		for (i in Math.round(-(hotbarSlots / 2f)) until Math.round((hotbarSlots / 2f))) {
			//Draw itemSlotTexture; at center minus offset when total slot count is even; at bottom
			batch.draw(itemSlotTexture,
					(itemSlotTextureSize * i) - (if (hotbarSlots % 2 == 0) {
						0.0f
					} else {
						0.5f
					}),
					(Settings.GAME_V_HEIGHT * -0.5f) / Settings.CURRENT_ASPECT_RATIO,
					0.5f,
					0.5f,
					1f,
					1f,
					itemSlotTextureSize,
					itemSlotTextureSize,
					0f)

		}

	}

}