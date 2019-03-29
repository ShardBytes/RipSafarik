package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

class Player : ILockable, ITickable{

    override val position: Vector2 = Vector2(0f, 0f)
    
    val playerSprite = Sprite(Texture("badlogic.jpg"))

    override fun tick(batch: SpriteBatch, deltaTime: Float) {
        playerSprite.draw(batch)
        
    }
    
}