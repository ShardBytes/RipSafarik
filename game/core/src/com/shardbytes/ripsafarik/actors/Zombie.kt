package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.GameWorld
import com.shardbytes.ripsafarik.tools.GameObject
import com.shardbytes.ripsafarik.tools.GifDecoder
import kotlin.math.abs

class Zombie(world: GameWorld, private val player: Player, private val zombieType: ZombieType) : GameObject {
    
    enum class ZombieType {
        NO_HAND,
        NO_HAND_BLOOD,
        HAND_BLOOD,
        RUNNER
    }

    private var isWalking = false
    private var elapsedTime = 0f
    private val animatedMonster = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(getTextureFromType()).read())
    
    override val position = Vector2()
    private val velocity = Vector2()
    private var direction = 0f 
    private var speed = 0f

    private var maxSpeed = 0f
    private var rotationSpeed = 200f
    
    //Animation properties
    private var frames = 0
    private var frameTime = 0
    
    private fun getTextureFromType() : String {
        return when(zombieType) {
            ZombieType.NO_HAND -> { frames = 4; frameTime = 150; maxSpeed = 1f; "textures/entity/animatedMonster.gif" }
            ZombieType.HAND_BLOOD -> { frames = 4; frameTime = 150; maxSpeed = 1f; "textures/entity/animatedMonster2.gif" }
            ZombieType.NO_HAND_BLOOD -> { frames = 4; frameTime = 150; maxSpeed = 1f; "textures/entity/animatedMonster3.gif" }
            ZombieType.RUNNER -> { frames = 4; frameTime = 100; maxSpeed = 3f; "textures/entity/animatedFastMonster.gif" }

        }
        
    }
    
    override fun act(dt: Float) {
        handleAI(dt, player.position)
        updatePhysics(dt)
    }
    
    override fun render(dt: Float, batch: SpriteBatch) {
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
        elapsedTime %= frames * frameTime * 0.001f
        
    }
    
    private fun updatePhysics(dt: Float) {
        position.mulAdd(velocity, dt)
    }
    
    private fun handleAI(deltaTime: Float, playerPos: Vector2) {
        val distance = playerPos.dst(position)
        if(distance < 20 && distance > 0.5) {
            isWalking = true
            speed = maxSpeed
            
        } else {
            isWalking = false
            speed = 0f
            
        }
        
        val optimalDirectionVector = playerPos.cpy().sub(position).nor()
        val optimalRotation = MathUtils.radiansToDegrees * MathUtils.atan2(optimalDirectionVector.y, optimalDirectionVector.x)
        
        var rotation = if(abs(optimalRotation - this.direction) > rotationSpeed * deltaTime) rotationSpeed * deltaTime else optimalRotation - this.direction
        if(optimalRotation > this.direction) {
            this.direction += rotation
            
        } else {
            this.direction -= rotation
            
        }

        val directionVector = Vector2(MathUtils.cosDeg(this.direction), MathUtils.sinDeg(this.direction))
        velocity.set(directionVector.setLength(speed))
        
    }
    
    override fun dispose() {}

}