package com.shardbytes.ripsafarik.components.technical

import com.shardbytes.ripsafarik.blocks.EmptyBlock
import com.shardbytes.ripsafarik.components.world.Block

object BlockCatalog {
	
	private var blocks: MutableMap<String, Block> = mutableMapOf(Pair("default", EmptyBlock()))
	
	fun getBlockCopy(blockId: String): Block {
		return blocks[blockId] ?: blocks["default"]!!
		
	}
	
	fun registerBlock(block: Block) {
		blocks[block.name] = block
		
	}
	
}