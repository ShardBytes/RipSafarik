package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

class Player : ILockable, ITickable{

    override val position: Vector2 = Vector2(0f, 0f)
    
    val sprite = Sprite(Texture("badlogic.jpg")).apply {
        setOriginCenter()
        setPosition(0f, 0f)
        setSize(1f, 1f)
        
    }

    override fun tick(batch: SpriteBatch, deltaTime: Float) {
        sprite.draw(batch)
        
    }
    
}