package com.shardbytes.ripsafarik.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.Settings
import com.shardbytes.ripsafarik.assets.Textures

object Hotbar {
	
	var hotbarSlots = 1
	
	private val itemSlotTexture = Textures.UI["itemslot"]
	private val itemSlotTextureSize = 1f
	
	fun render(dt: Float, batch: SpriteBatch) {
		batch.draw(TextureRegion(itemSlotTexture), -7.5f, 6.5f / Settings.CURRENT_ASPECT_RATIO, 0.5f, 0.5f, 1f, 1f, itemSlotTextureSize, itemSlotTextureSize, 0f)
		batch.draw(TextureRegion(itemSlotTexture), 6.5f, 6.5f / Settings.CURRENT_ASPECT_RATIO, 0.5f, 0.5f, 1f, 1f, itemSlotTextureSize, itemSlotTextureSize, 0f)
		
		batch.draw(TextureRegion(itemSlotTexture), -7.5f, -7.5f / Settings.CURRENT_ASPECT_RATIO, 0.5f, 0.5f, 1f, 1f, itemSlotTextureSize, itemSlotTextureSize, 0f)
		batch.draw(TextureRegion(itemSlotTexture), 6.5f, -7.5f / Settings.CURRENT_ASPECT_RATIO, 0.5f, 0.5f, 1f, 1f, itemSlotTextureSize, itemSlotTextureSize, 0f)
		
		
		
	}
	
}