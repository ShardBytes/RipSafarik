package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class World : ITickable {
    
    val tileMap = arrayOf(
            arrayOf(0, 0, 0, 0, 0),
            arrayOf(1, 1, 1, 1, 1),
            arrayOf(0, 0, 0, 0, 0)
    )
    
    val textureMap = mapOf(
            0 to Texture("asfalt.png"),
            1 to Texture("obrubnik.png")
    
    )

    override fun tick(batch: SpriteBatch, deltaTime: Float) {
        for ((y, row) in tileMap.withIndex()) {
            for ((x, value) in row.withIndex()) {
                batch.draw(textureMap[value] ?: textureMap[0], x.toFloat(), y.toFloat(), 1f, 1f)
                
            }
            
        }
        
    }
    
}