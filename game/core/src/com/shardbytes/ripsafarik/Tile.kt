package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable

class Tile(private val texture: Texture,
           private val rotation: Int) : Disposable {
    
    private val region = TextureRegion(texture)
    
    override fun dispose() {
        texture.dispose()
        
    }

    fun draw(batch: SpriteBatch, x: Float, y: Float) {
        batch.draw(region, x, y, x/2, y/2, 1f, 1f, 1f, 1f, rotation.toFloat(), true)
        
    }
    
}