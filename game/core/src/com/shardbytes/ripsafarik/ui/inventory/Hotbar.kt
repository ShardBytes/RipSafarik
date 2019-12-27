package com.shardbytes.ripsafarik.ui.inventory

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.Settings
import com.shardbytes.ripsafarik.actors.GameWorld
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.IUsable
import com.shardbytes.ripsafarik.ui.Healthbar

object Hotbar {

	var slotCount = 4
	var selectedSlot = 0
	
	val hotbarSlots = createSlots()

	fun render(dt: Float, batch: SpriteBatch) {
		drawHotbarSlots(batch)
		//drawSelectedSlot(batch) //Draw that one slot one more time to make it less transparent for now
								//TODO: you can do better <-- graphics i meant

		//drawItems(batch)

	}
/*
	private fun drawSelectedSlot(batch: SpriteBatch) {
		val slotNumber = selectedSlot - Math.round(slotCount / 2f)

		batch.draw(itemSlotTexture,
				(itemSlotTextureSize * slotNumber) - (if (slotCount % 2 == 0) {
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
		for (i in Math.round(-(slotCount / 2f)) until Math.round((slotCount / 2f))) {
			val item = GameWorld.player.inventory.hotbar[i + Math.round(slotCount / 2f)]

			if (item != null) {
				batch.draw(item.texture,
						(itemSlotTextureSize * i) - (if (slotCount % 2 == 0) {
							0.0f
						} else {
							0.5f
						}),
						(Settings.GAME_V_HEIGHT * -0.5f) / Settings.CURRENT_ASPECT_RATIO,
						0.5f, //Rotation origin
						0.5f, //Ah i see
						1f,
						1f,
						itemSlotTextureSize * 0.66f, //Make item sligthly smaller in the inventory
						itemSlotTextureSize * 0.66f, //TODO: scale is good?
						0f)

				if(item is IUsable) {
					if(item.maxUses != 0) {
						val usesLeft = Healthbar[(item.leftUses.toFloat() / item.maxUses.toFloat() * 100f).toInt()]
						batch.draw(usesLeft,
								(itemSlotTextureSize * i) - (if (slotCount % 2 == 0) {
									0.0f
								} else {
									0.5f
								}),
								(Settings.GAME_V_HEIGHT * -0.5f) / Settings.CURRENT_ASPECT_RATIO,
								0.5f,
								0.5f,
								1f,
								0.05f,
								itemSlotTextureSize * 0.66f,
								itemSlotTextureSize * 0.66f,
								0f)

					}

				}

			}

		}

	}
*/
	private fun drawHotbarSlots(batch: SpriteBatch) {
		hotbarSlots.forEach { it.render(batch) }

	}

	private fun createSlots(): Array<ItemSlot> {
		val slots = arrayListOf<ItemSlot>()
		for (i in Math.round(-(slotCount / 2f)) until Math.round((slotCount / 2f))) {
			slots.add(ItemSlot().apply { 
				screenPosition = Vector2(
						((slotSize * i) - if (slotCount % 2 == 0) 0f else 0.5f) + slotSize * 0.5f,
						Settings.VISIBLE_SCREEN_HEIGHT_GUI * -0.5f + slotSize * 0.5f)
				
			})
			
		}
		return slots.toArray(arrayOfNulls(slotCount))

	}
	
	fun updateSlotPositions() {
		hotbarSlots.forEach { 
			it.screenPosition.set(it.screenPosition.x, Settings.VISIBLE_SCREEN_HEIGHT_GUI * -0.5f + it.slotSize * 0.5f)
			
		}
		
	}

}