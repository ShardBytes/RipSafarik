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
            arrayOf("0a", "0a", "1a", "1a", "1a", "1a", "1a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a"),
            
            arrayOf("0a", "0a", "1a", "1a", "1a", "1a", "1a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a"),
            
            arrayOf("0a", "0a", "1a", "1a", "1a", "1a", "1a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a"),
            
            arrayOf("0a", "0a", "1a", "1a", "1a", "1a", "1a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a"),
            
            arrayOf("0a", "0a", "1a", "1a", "1a", "1a", "1a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a"),
            
            arrayOf("0a", "0a", "1a", "1a", "1a", "1a", "1a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a"),
            
            arrayOf("3a", "3a", "1a", "1a", "1a", "1a", "1a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a"),
            
            arrayOf("1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a"),
            
            arrayOf("2a", "2a", "1a", "2a", "2a", "1a", "2a", "2a", "1a", "2a", "2a", "1a", "2a", "2a", "1a", "2a", "2a", "1a", "2a", "2a", "1a"),
            
            arrayOf("1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a"),
            
            arrayOf("3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c"),
            
            arrayOf("4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a"),

            arrayOf("6c", "6c", "6c", "6c", "6c", "6c", "4a", "4a", "5b", "5b", "5b", "8a", "8a", "8a", "8a", "5d", "5d", "5d", "4a", "4a", "6c"),

            arrayOf("7c", "7c", "7c", "7c", "7c", "7c", "4a", "4a", "5b", "5b", "5b", "8a", "8a", "8a", "8a", "5d", "5d", "5d", "4a", "4a", "7c")
    ).reversedArray()

    val spriteMap = mapOf(
            "0a" to Sprite(Texture("textures/grass.png")),
            "1a" to Sprite(Texture("textures/asfalt.png")),
            "2a" to Sprite(Texture("textures/asfalt_ciara.png")),
            "3a" to Sprite(Texture("textures/obrubnik.png")),
            "3b" to Sprite(Texture("textures/obrubnik.png")),
            "3c" to Sprite(Texture("textures/obrubnik.png")),
            "3d" to Sprite(Texture("textures/obrubnik.png")),
            "4a" to Sprite(Texture("textures/beton.png")),
            "5a" to Sprite(Texture("textures/stairs.png")),
            "5b" to Sprite(Texture("textures/stairs.png")),
            "5c" to Sprite(Texture("textures/stairs.png")),
            "5d" to Sprite(Texture("textures/stairs.png")),
            "6c" to Sprite(Texture("textures/wall.png")),
            "7c" to Sprite(Texture("textures/roof.png")),
            "8a" to Sprite(Texture("textures/floor.png"))

    ).onEach {
        it.value.apply {
            setSize(1f, 1f)
            setOriginCenter()
        }

        when(it.key.last()) {
            'b' -> it.value.apply { rotation = 90f }
            'c' -> it.value.apply { rotation = 180f }
            'd' -> it.value.apply { rotation = 270f }

        }
        
    }

    override fun tick(batch: SpriteBatch, dt: Float) {
        for ((y, row) in tileMap.withIndex()) {
            for ((x, value) in row.withIndex()) {
                spriteMap[value]!!.apply { setPosition(x.toFloat(), y.toFloat()) }.draw(batch)
                
            }
            
        }
        
    }

}
