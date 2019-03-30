package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class World : ITickable {
    /*
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
            arrayOf(4, 4, 4, 4, 3, 3, 3, 5, 5, 4)
    ).reversedArray()
    */
    
    val tileMap = arrayOf(
            arrayOf("0a", "0a", "0a", "0a", "0a"),
            arrayOf("1a", "1a", "1a", "1a", "1a"),
            arrayOf("0a", "0a", "4a", "4a", "5b"),
            arrayOf("4a", "4a", "4a", "4a", "4a")
    ).reversedArray()
    
    val spriteMap = mapOf(
            "0a" to Sprite(Texture("grass.png")),
            "1a" to Sprite(Texture("asfalt.png")),
            "2a" to Sprite(Texture("asfalt_ciara.png")),
            "3a" to Sprite(Texture("obrubnik.png")),
            "4a" to Sprite(Texture("beton.png")),
            "5a" to Sprite(Texture("stairs.png")).apply { setSize(1f, 1f); setOriginCenter()},
            "5b" to Sprite(Texture("stairs.png")).apply { setSize(1f, 1f); setOriginCenter(); rotation = 90f }
    
    )

    override fun tick(batch: SpriteBatch, deltaTime: Float) {
        for ((y, row) in tileMap.withIndex()) {
            for ((x, value) in row.withIndex()) {
                //batch.draw(textureMap[value] ?: textureMap[0], x.toFloat(), y.toFloat(), 1f, 1f)
                spriteMap[value]!!.apply { setPosition(x.toFloat(), y.toFloat()) }.draw(batch)
                
            }
            
        }
        
    }
    
}