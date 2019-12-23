package com.shardbytes.ripsafarik.ui.inventory

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.IUsable
import com.shardbytes.ripsafarik.components.Item
import com.shardbytes.ripsafarik.items.Gun
import com.shardbytes.ripsafarik.ui.Healthbar

class ItemSlot {

    var item: Item? = Gun()
    var screenPosition = Vector2()

    val slotTexture = TextureRegion(Textures.UI["itemslot"])
    val slotSize = 1f

    fun render(batch: SpriteBatch) {
        batch.draw(slotTexture,
                screenPosition.x - 0.5f,
                screenPosition.y - 0.5f,
                0.5f,
                0.5f,
                1f,
                1f,
                1f,
                1f,
                0f)

        if(item != null) {
            batch.draw(item!!.texture,
                    screenPosition.x - 0.5f,
                    screenPosition.y - 0.5f,
                    0.5f,
                    0.5f,
                    1f,
                    1f,
                    slotSize - 0.33f,
                    slotSize - 0.33f,
                    0f)

            val usableItem = item as? IUsable
            if(usableItem != null) {
                if(usableItem.maxUses != 0) {
                    val usesLeft = Healthbar[(usableItem.leftUses.toFloat() / usableItem.maxUses.toFloat() * 100f).toInt()]
                    batch.draw(usesLeft,
                            screenPosition.x - 0.5f,
                            screenPosition.y - 0.5f,
                            0.5f,
                            0.5f,
                            1f,
                            0.05f,
                            slotSize - 0.33f,
                            slotSize - 0.33f,
                            0f)

                }

            }

        }

    }

    fun clicked() {

    }

}