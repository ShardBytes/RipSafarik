package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils

class Player : ILockable, ITickable{
    
    val sprite = Sprite(Texture("badlogic.jpg")).apply {
        setSize(1f, 1f)
        setOriginCenter()
        setPosition(0f, 0f)

    }
    
    override var position: Vector2 get() {
        return Vector2(sprite.x, sprite.y)
        
    } set(value) {
        sprite.setPosition(value.x, value.y)
        
    }
    
    private val maxSpeed = 1.0
    private val rotationSpeed = 2.5

    override fun tick(batch: SpriteBatch, deltaTime: Float) {
        handleInput(deltaTime)
        sprite.draw(batch)
        
    }

    private fun handleInput(delta: Float) {
        /*
		 * Movement
		 */
        val rotation = sprite.getRotation()
        var xAmount = 0.0
        var yAmount = 0.0

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            xAmount = -maxSpeed * MathUtils.sinDeg(rotation)
            yAmount = maxSpeed * MathUtils.cosDeg(rotation)
            
            
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            xAmount = maxSpeed * MathUtils.sinDeg(rotation)
            yAmount = -maxSpeed * MathUtils.cosDeg(rotation)
            
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            sprite.rotate(rotationSpeed.toFloat())
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            sprite.rotate((-rotationSpeed).toFloat())
        }

        position.add(xAmount.toFloat() * delta, yAmount.toFloat() * delta)
        sprite.translate(xAmount.toFloat() * delta, yAmount.toFloat() * delta)

        /*
		 * Shooting
		 */
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            //shoot()
        }

    }
    
}