package com.shardbytes.ripsafarik.items

import com.shardbytes.ripsafarik.components.world.Block
import com.shardbytes.ripsafarik.components.world.Item

class BlockItem(block: Block) : Item {
	
	override val name: String = block.name
	override val displayName: String = block.displayName
	override val texture = block.texture
	
}