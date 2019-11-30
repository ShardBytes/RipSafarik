package com.shardbytes.ripsafarik.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.Settings
import com.shardbytes.ripsafarik.assets.Textures

object Hotbar {
	
	var hotbarSlots = 4
	
	private val itemSlotTexture = Textures.UI["itemslot"]
	private val itemSlotTextureSize = 1f
	
	val locations = mapOf(
			"BL" to Vector2(-0.5f, -0.5f),
			"BR" to Vector2(0.5f, -0.5f),
			"BC" to Vector2(0f, -0.5f),
			
			"CL" to Vector2(-0.5f, 0f),
			"CR" to Vector2(0.5f, 0f),
			"CC" to Vector2(0f, 0f),
			
			"TL" to Vector2(-0.5f, 0.5f),
			"TR" to Vector2(0.5f, 0.5f),
			"TC" to Vector2(0f, 0.5f) 
			).mapValues {
		it.value.x *= Settings.GAME_V_WIDTH
		it.value.y *= Settings.GAME_V_HEIGHT
		
		when (it.key){
			"CL" -> it.value.y -= itemSlotTextureSize / 2
			"BC" -> it.value.x -= itemSlotTextureSize / 2
		}
		
		it.value
		
	}
	
	fun render(dt: Float, batch: SpriteBatch) {
		for (i in Math.round(-(hotbarSlots / 2f)) until Math.round((hotbarSlots / 2f))) {
			//Draw itemSlotTexture                        at bottom center              minus offset when total slot count is even               at bottom center
			batch.draw(TextureRegion(itemSlotTexture), (itemSlotTextureSize * i) - (if (hotbarSlots % 2 == 0) { 0.0f } else { 0.5f }), (Settings.GAME_V_HEIGHT * -0.5f) / Settings.CURRENT_ASPECT_RATIO, 0.5f, 0.5f, 1f, 1f, itemSlotTextureSize, itemSlotTextureSize, 0f)
			
		}
		
		
	}
	
}