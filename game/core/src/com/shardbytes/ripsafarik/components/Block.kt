package com.shardbytes.ripsafarik.components

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import ktx.box2d.FixtureDefinition

interface Block {

	val name: String
	val texture: Texture
	val body: FixtureDefinition?
	
	fun render(dt: Float, batch: SpriteBatch, pos: Vector2) {
		batch.draw(TextureRegion(texture), pos.x - 0.5f, pos.y - 0.5f, 0.5f, 0.5f, 1f, 1f, 1f, 1f, 0f)
		
	}
	
}