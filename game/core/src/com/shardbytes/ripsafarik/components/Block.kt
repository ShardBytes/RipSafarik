package com.shardbytes.ripsafarik.components

import com.badlogic.gdx.graphics.Texture

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
	 * Block texture.
	 * @see Texture
	 * @see com.shardbytes.ripsafarik.assets.Textures
	 */
	val texture: Texture
	
}