package com.shardbytes.ripsafarik.components

/**
 * Item storage class, this can be used for any entity that can hold items.
 */
class ItemInventory {
	
	var items: Array<Item?> = Array(9) { null }
	var hotbar: Array<Item?> = Array(4) { null }

	fun pickUpItem(item: Item) {
		hotbar.forEachIndexed { index, item2 -> if (item2 == null) { hotbar[index] = item; return } }

	}
	
}