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
		drawSelectedSlot(batch)

	}

	private fun drawSelectedSlot(batch: SpriteBatch) {
		hotbarSlots[selectedSlot].drawSlotMarker(batch)

	}

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