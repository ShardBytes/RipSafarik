package com.shardbytes.ripsafarik.components

import com.badlogic.gdx.graphics.Texture

interface Item {

	/**
	 * Technical name.
	 * @see Block
	 */
	val name: String

	/**
	 * Item texture.
	 */
	val texture: Texture

	/**
	 * Item name that's displayed to the user.
	 */
	val displayName: String
	
}