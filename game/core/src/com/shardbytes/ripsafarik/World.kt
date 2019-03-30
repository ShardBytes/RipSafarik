package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class World : ITickable {
    
    val tileMap = arrayOf(
            arrayOf(4, 4, 0, 0, 0),
            arrayOf(4, 4, 0, 0, 0),
            arrayOf(4, 4, 0, 0, 0),
            arrayOf(4, 4, 0, 0, 0),
            arrayOf(3, 3, 0, 0, 0, 3, 3, 3, 3, 3),
            arrayOf(0, 0, 0, 0, 0, 2, 2, 2, 2, 2),
            arrayOf(0, 1, 0, 1, 0, 1, 0, 1, 0, 1),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(3, 3, 3, 3, 3, 3, 3, 3, 3, 3),
            arrayOf(4, 4, 4, 4, 3, 3, 3, 4, 4, 4)
    ).reversedArray()
    
    val textureMap = mapOf(
            0 to Texture("textures/asfalt.png"),
            1 to Texture("textures/asfalt_ciara.png"),
            2 to Texture("textures/obrubnik.png"),
            3 to Texture("textures/beton.png"),
            4 to Texture("textures/grass.png")
    
    )

    override fun tick(batch: SpriteBatch, deltaTime: Float) {
        for ((y, row) in tileMap.withIndex()) {
            for ((x, value) in row.withIndex()) {
                batch.draw(textureMap[value] ?: textureMap[0], x*1f - 0.5f, y*1f - 0.5f, 1f, 1f)
            }
        }
    }
    
}