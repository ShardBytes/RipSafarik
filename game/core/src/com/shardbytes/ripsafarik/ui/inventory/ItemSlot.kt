package com.shardbytes.ripsafarik.ui.inventory

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.Vector2Serializer
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.inRange
import com.shardbytes.ripsafarik.items.ItemStack
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

//@Serializable
class ItemSlot {

    var itemStack: ItemStack? = null

    //@Serializable(with = Vector2Serializer::class)
    var screenPosition = Vector2()

    //@Transient
    val slotTexture = TextureRegion(Textures.UI["itemslot"])
    //@Transient
    val slotMarkerTexture = TextureRegion(Textures.UI["itemslotmarker"])
    //@Transient
    val slotSize = 1f

    fun render(batch: SpriteBatch) {
        drawSlotTexture(batch)
        drawItem(batch)

    }

    fun drawSlotMarker(batch: SpriteBatch) {
        batch.draw(slotMarkerTexture,
                screenPosition.x - slotSize * 0.5f,
                screenPosition.y - slotSize * 0.5f,
                0.5f,
                0.5f,
                1f,
                1f,
                slotSize,
                slotSize,
                0f)

    }

    private fun drawSlotTexture(batch: SpriteBatch) {
        batch.draw(slotTexture,
                screenPosition.x - slotSize * 0.5f,
                screenPosition.y - slotSize * 0.5f,
                0.5f,
                0.5f,
                1f,
                1f,
                slotSize,
                slotSize,
                0f)
    }

    private fun drawItem(batch: SpriteBatch) {
        itemStack?.render(batch, screenPosition, slotSize)

    }

    fun isCoordinateInsideSlot(coordinate: Vector2): Boolean {
        val start = screenPosition.cpy().sub(slotSize * 0.5f, slotSize * 0.5f)
        val end = screenPosition.cpy().add(slotSize * 0.5f, slotSize * 0.5f)

        return coordinate.inRange(start, end)

    }

    /*
     * oh jeez
     * works exactly like Minecraft inventory
     * mission successful
     */
    fun clicked(rightClick: Boolean) {
        if (!rightClick) {
            if (itemStack?.hasTheSameItemAs(PlayerInventory.floatingItemStack) ?: false) {
                itemStack!!.amount += PlayerInventory.floatingItemStack?.amount ?: 0
                PlayerInventory.floatingItemStack = null

            } else {
                val buffer = itemStack
                itemStack = PlayerInventory.floatingItemStack
                PlayerInventory.floatingItemStack = buffer

            }

        } else {
            if (PlayerInventory.floatingItemStack == null) {
                PlayerInventory.floatingItemStack = itemStack?.half()

            } else {
                if (itemStack?.hasTheSameItemAs(PlayerInventory.floatingItemStack) ?: false) {
                    itemStack!!.amount += 1
                    PlayerInventory.floatingItemStack = PlayerInventory.floatingItemStack?.oneLess()

                } else {
                    itemStack = PlayerInventory.floatingItemStack?.splitOne()

                }

            }

        }

    }

}