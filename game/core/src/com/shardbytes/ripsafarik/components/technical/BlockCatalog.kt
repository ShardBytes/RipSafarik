package com.shardbytes.ripsafarik.components.technical

import com.shardbytes.ripsafarik.components.world.Block

object BlockCatalog {
	
	private var blocks: MutableMap<String, Block> = mutableMapOf()
	
	operator fun get(blockId: String) = blocks[blockId]
	
	fun registerBlock(block: Block) {
		blocks.put(block.name, block)
		
	}
	
}