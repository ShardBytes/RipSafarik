package com.shardbytes.ripsafarik

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import kotlin.math.abs

class Zombie(private val whereIsPlayer: Player) : ILockable, ITickable {

    private var isWalking = false
    private var elapsedTime = 0f
    private val animatedMonster = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("textures/animatedMonster.gif").read())
    
    override val position = Vector2()
    private val velocity = Vector2()
    private var direction = 0f 
    private var speed = 0f

    private val maxSpeed = 1.0f
    private val rotationSpeed = 200f
    
    override fun tick(batch: SpriteBatch, dt: Float) {
        handleAI(dt, whereIsPlayer.position)
        position.mulAdd(velocity, dt)

        val width = 1f
        val height = 1f
        val originX = width / 2
        val originY = height / 2
        val originBasedPositionX = position.x - originX
        val originBasedPositionY = position.y - originY

        if(isWalking) {
            batch.draw(animatedMonster.getKeyFrame(elapsedTime), originBasedPositionX, originBasedPositionY, originX, originY, width, height, 1f, 1f, direction - 90f)

        } else {
            batch.draw(animatedMonster.getKeyFrame(0.25f), originBasedPositionX, originBasedPositionY, originX, originY, width, height, 1f, 1f, direction - 90f)

        }
        elapsedTime += dt
        elapsedTime %= 0.8f
        
    }
    
    private fun handleAI(deltaTime: Float, playerPos: Vector2) {
        val distance = playerPos.dst(position)
        if(distance < 20 && distance > 0.2) {
            isWalking = true
            speed = maxSpeed
            
        } else {
            isWalking = false
            speed = 0f
            
        }
        
        val optimalDirectionVector = playerPos.cpy().sub(position).nor()
        val optimalRotation = MathUtils.radiansToDegrees * MathUtils.atan2(optimalDirectionVector.y, optimalDirectionVector.x)
        
        //var rotation = if(abs(optimalRotation - this.direction) > rotationSpeed * deltaTime) rotationSpeed * deltaTime else optimalRotation
        //var directionVector = Vector2(MathUtils.cosDeg(rotation), MathUtils.sinDeg(rotation))
        
        this.direction = optimalRotation
        velocity.set(optimalDirectionVector.setLength(speed))
        
    }

}