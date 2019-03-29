package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface ITickable {
    
    fun tick(batch: SpriteBatch, deltaTime: Float)
    
}