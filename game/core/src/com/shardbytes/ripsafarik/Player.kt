package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils

class Player : ILockable, ITickable{
    
    val sprite = Sprite(Texture("player.png")).apply {
        setSize(1f, 1f)
        setOriginCenter()
    }
    
    override val position = Vector2(0f, 0f)
    var direction = 0f // degrees
    val velocity = Vector2(1f, 0f)
    
    private val maxSpeed = 2.0f
    private val rotationSpeed = 150f

    override fun tick(batch: SpriteBatch, dt: Float) {
        // handle input
        handleInput(dt)
        
        // update physics
        position.mulAdd(velocity, dt)
        
        // update and draw sprite
        sprite.setPosition(position.x, position.y) // update absolute sprite position
        sprite.rotation = direction - 90f
        sprite.draw(batch)
    }

    private fun handleInput(dt: Float) {
        /*
		 * Movement
		 */
        var speed = 0f

        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            speed = maxSpeed
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            speed = -maxSpeed
        else
            speed = 0f
        
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction += rotationSpeed * dt
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction -= rotationSpeed * dt
        }
        
        // update velocity by speed and direction
        velocity.set(1f, 0f).setAngle(direction).rotate(if (speed < 0) 180f else 0f).setLength(speed)

        /*
		 * Shooting
		 */
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            //shoot()
        }

    }
    
}