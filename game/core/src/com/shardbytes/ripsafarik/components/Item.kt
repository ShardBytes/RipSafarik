package com.shardbytes.ripsafarik.components

import com.badlogic.gdx.graphics.g2d.TextureRegion

interface Item {

	/**
	 * Technical name.
	 * @see Block
	 */
	val name: String

	/**
	 * Item name that's displayed to the user.
	 */
	val displayName: String

	/**
	 * Item texture.
	 * TextureRegion for better performance when not animated.
	 */
	val texture: TextureRegion
	
}