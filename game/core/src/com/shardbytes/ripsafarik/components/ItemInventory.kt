package com.shardbytes.ripsafarik.components

class ItemInventory {
	
	var items: Array<Item?> = Array(10) { null }
	var hotbar: Array<Item?> = Array(10) { null }

	fun pickUpItem(item: Item) {
		hotbar.forEachIndexed { index, item2 -> if (item2 == null) { hotbar[index] = item; return } }

	}
	
}