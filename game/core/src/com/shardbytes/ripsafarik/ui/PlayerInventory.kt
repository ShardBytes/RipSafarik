package com.shardbytes.ripsafarik.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.actors.GameWorld
import com.shardbytes.ripsafarik.assets.Textures

/**
 * Visual player inventory object. Contains rendering and stuff. Uses ItemInventory
 * to get information.
 * @see ItemInventory
 */
object PlayerInventory {

    var isOpened = false

    private val inventoryTexture = TextureRegion(Textures.UI["inventorybackground"])
    private val itemSlotTexture = TextureRegion(Textures.UI["itemslot"])

    fun render(dt: Float, batch: SpriteBatch) {
        if(isOpened) {
            drawBackground(batch)
            drawSlots(batch)
            drawItems(batch)

        }

    }

    private fun drawBackground(batch: SpriteBatch) {
        batch.draw(inventoryTexture,
                0f - 1.5f, //  - width/2
                0f - 1.5f, // - height/2
                1.5f,
                1.5f,
                3f,
                3f,
                1f,
                1f,
                0f)
    }

    private fun drawSlots(batch: SpriteBatch) {
        for (i in -1..1) {
            for (j in -1..1) {
                batch.draw(itemSlotTexture,
                        j.toFloat() - 0.5f,
                        i.toFloat() - 0.5f,
                        0.5f,
                        0.5f,
                        1f,
                        1f,
                        1f,
                        1f,
                        0f)

            }

        }
    }

    private fun drawItems(batch: SpriteBatch) {
        for (i in -1..1) {
            for (j in -1..1) {
                val item = GameWorld.player.inventory.items[(i + 1) * 3 + (j + 1)]

                if (item != null) {
                    batch.draw(item.texture,
                            j.toFloat() - 0.5f,
                            i.toFloat() - 0.5f,
                            0.5f,
                            0.5f,
                            1f,
                            1f,
                            1f - 0.33f,
                            1f - 0.33f,
                            0f)


                }

            }

        }
        
    }

}