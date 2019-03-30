package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class World : ITickable {

    private val spriteMap = mapOf(
            "0a" to Sprite(Texture("textures/env/grass.png")),
            "1a" to Sprite(Texture("textures/env/asfalt.png")),
            "2a" to Sprite(Texture("textures/env/asfalt_ciara.png")),
            "3a" to Sprite(Texture("textures/env/obrubnik.png")),
            "3b" to Sprite(Texture("textures/env/obrubnik.png")),
            "3c" to Sprite(Texture("textures/env/obrubnik.png")),
            "3d" to Sprite(Texture("textures/env/obrubnik.png")),
            "4a" to Sprite(Texture("textures/env/beton.png")),
            "5a" to Sprite(Texture("textures/env/stairs.png")),
            "5b" to Sprite(Texture("textures/env/stairs.png")),
            "5c" to Sprite(Texture("textures/env/stairs.png")),
            "5d" to Sprite(Texture("textures/env/stairs.png")),
            "6c" to Sprite(Texture("textures/env/wall.png")),
            "7c" to Sprite(Texture("textures/env/roof.png")),
            "8a" to Sprite(Texture("textures/env/floor.png")),
            "9a" to Sprite(Texture("textures/orbs/runningorb.png"))

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

    private val tileMap = arrayOf(
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
    
    private val overlayMap = arrayOf(
            spriteMap.getValue("9a").apply { setSize(1f, 1f); setOriginCenter(); setPosition(13f, 0f) },
            spriteMap.getValue("9a").apply { setSize(1f, 1f); setOriginCenter(); setPosition(13f, 8f) }
            
    )

    override fun tick(batch: SpriteBatch, dt: Float) {
        for ((y, row) in tileMap.withIndex()) {
            for ((x, value) in row.withIndex()) {
                spriteMap.getValue(value).apply { setPosition(x.toFloat(), y.toFloat()) }.draw(batch)
                
            }
            
        }
        
        overlayMap.forEach { 
            it.draw(batch)
            
        }
        
    }

}
