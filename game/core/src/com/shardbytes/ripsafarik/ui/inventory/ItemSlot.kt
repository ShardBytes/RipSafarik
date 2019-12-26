package com.shardbytes.ripsafarik.ui.inventory

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.IUsable
import com.shardbytes.ripsafarik.components.Item
import com.shardbytes.ripsafarik.inRange
import com.shardbytes.ripsafarik.items.Gun
import com.shardbytes.ripsafarik.ui.Healthbar

class ItemSlot {

    var item: Item? = Gun()
    var screenPosition = Vector2()

    val slotTexture = TextureRegion(Textures.UI["itemslot"])
    val slotSize = 1f

    fun render(batch: SpriteBatch) {
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

        if(item != null) {
            batch.draw(item!!.texture,
                    screenPosition.x - slotSize * 0.5f,
                    screenPosition.y - slotSize * 0.5f,
                    0.5f,
                    0.5f,
                    1f,
                    1f,
                    slotSize * 0.66f,
                    slotSize * 0.66f,
                    0f)

            val usableItem = item as? IUsable
            if(usableItem != null) {
                if(usableItem.maxUses != 0) {
                    val usesLeft = Healthbar[(usableItem.leftUses.toFloat() / usableItem.maxUses.toFloat() * 100f).toInt()]
                    batch.draw(usesLeft,
                            screenPosition.x - slotSize * 0.5f,
                            screenPosition.y - slotSize * 0.5f,
                            0.5f,
                            0.5f,
                            1f,
                            0.05f, // 1/20th
                            slotSize * 0.66f,
                            slotSize * 0.66f,
                            0f)

                }

            }

        }

    }

    fun isCoordinateInsideSlot(coordinate: Vector2): Boolean {
        val start = screenPosition.cpy().sub(slotSize * 0.5f, slotSize * 0.5f)
        val end = screenPosition.cpy().add(slotSize * 0.5f, slotSize * 0.5f)
        
        return coordinate.inRange(start, end)
        
    }
    
    fun clicked() {
        val start = screenPosition.cpy().sub(slotSize * 0.5f, slotSize * 0.5f)
        val end = screenPosition.cpy().add(slotSize * 0.5f, slotSize * 0.5f)

        screenPosition = Vector2(-3f, 0f)
        
        println("$start .. $end")
        println(this)
        
    }

}