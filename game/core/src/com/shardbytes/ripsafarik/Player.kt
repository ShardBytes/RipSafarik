package com.shardbytes.ripsafarik

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

class Player : ILockable, ITickable {
    
    private var isWalking = false
    private var elapsedTime = 0f
    private val animatedPlayer = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("textures/animatedPlayer.gif").read())
    
    override val position = Vector2()
    private val velocity = Vector2()
    private var direction = 0f
    private var speed = 0f
    
    private val maxSpeed = 2.0f
    private val rotationSpeed = 200f //TODO: tweak this shit

    override fun tick(batch: SpriteBatch, dt: Float) {
        // handle input
        handleInput(dt)
        
        // update physics
        position.mulAdd(velocity, dt)
        
        val width = 1f
        val height = 1f
        val originX = width / 2
        val originY = height / 2
        val originBasedPositionX = position.x - originX
        val originBasedPositionY = position.y - originY
        
        if(isWalking) {
            batch.draw(animatedPlayer.getKeyFrame(elapsedTime), originBasedPositionX, originBasedPositionY, originX, originY, width, height, 1f, 1f, direction - 90f)
            
        } else {
            batch.draw(animatedPlayer.getKeyFrame(0.25f), originBasedPositionX, originBasedPositionY, originX, originY, width, height, 1f, 1f, direction - 90f)
            
        }
        elapsedTime += dt
        elapsedTime %= 0.8f
        
    }

    private fun handleInput(dt: Float) {
        //Movement
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            isWalking = true
            speed = maxSpeed

        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            isWalking = true
            speed = -maxSpeed

        } else {
            isWalking = false
            speed = 0f
            
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction += rotationSpeed * dt
            
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction -= rotationSpeed * dt
            
        }
        
        // update velocity by speed and direction
        velocity.set(1f, 0f).setAngle(direction).rotate(if (speed < 0) 180f else 0f).setLength(speed)
        
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            //shoot()
            
        }

    }
    
}