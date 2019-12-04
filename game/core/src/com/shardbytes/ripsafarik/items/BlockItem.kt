package com.shardbytes.ripsafarik.items

import com.shardbytes.ripsafarik.components.Block
import com.shardbytes.ripsafarik.components.Item

class BlockItem(block: Block) : Item {
	
	override val name: String = block.name
	override val displayName: String = block.displayName
	override val texture = block.texture
	
}