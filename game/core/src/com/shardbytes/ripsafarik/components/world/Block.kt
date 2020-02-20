package com.shardbytes.ripsafarik.components.world

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

interface Block {

	/**
	 * Block name defines the name used in block catalog and in map files.
	 * @see Catalog
	 */
	val name: String

	/**
	 * Name that is shown to the user, eg. when inside an inventory.
	 */
	val displayName: String

	/**
	 * Block texture. TextureRegion for better performance when not animated.
	 * @see TextureRegion
	 * @see com.shardbytes.ripsafarik.assets.Textures
	 */
	val texture: TextureRegion
	
	fun onCreate(coords: Vector2) {}
	fun onDestroy(coords: Vector2) {}
	fun createCollider(coords: Vector2) {}
	
	fun render(batch: SpriteBatch, x: Float, y: Float, sizeX: Float = 1f, sizeY: Float = 1f, rotation: Float = 0f) {
		batch.draw(TextureRegion(texture), x - sizeX * 0.5f, y - sizeY * 0.5f, sizeX * 0.5f, sizeY * 0.5f, sizeX, sizeY, 1f, 1f, rotation)
		
	}

}