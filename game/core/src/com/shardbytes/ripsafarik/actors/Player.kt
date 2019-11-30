package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.Gdx.graphics
import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils.radDeg
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.shardbytes.ripsafarik.assets.Animations
import com.shardbytes.ripsafarik.components.Entity
import com.shardbytes.ripsafarik.components.ItemInventory
import ktx.box2d.body
import kotlin.math.sqrt

class Player(physics: World) : Entity {
    
    private val width = 1f
    private val height = 1f
    private val maxSpeed = 4f //TODO: What unit?
    
    private var isWalking = false
    private var elapsedTime = 0f 
    private val animatedPlayer = Animations["animatedPlayer"]
    
    val inventory: ItemInventory = ItemInventory()
    
    override val body = physics.body(BodyDef.BodyType.DynamicBody) {
        circle(radius = width*0.5f) { userData = this@Player }
        fixedRotation = true

    }
    
    override fun tick(dt: Float) {
        handleInput()
        
    }
    
    override fun render(dt: Float, batch: SpriteBatch) {
        val originX = width * 0.5f
        val originY = height * 0.5f
        val originBasedPositionX = position.x - originX
        val originBasedPositionY = position.y - originY

        if(isWalking) {
            batch.draw(animatedPlayer.getKeyFrame(elapsedTime), originBasedPositionX, originBasedPositionY, originX, originY, width, height, 1f, 1f, body.angle * radDeg - 90f)

        } else {
            batch.draw(animatedPlayer.getKeyFrame(0.25f), originBasedPositionX, originBasedPositionY, originX, originY, width, height, 1f, 1f, body.angle * radDeg - 90f)

        }
        elapsedTime += dt
        elapsedTime %= 0.8f //4 animation frames @ 200ms per frame rate
        
    }

    private fun handleInput() {
        val invsqrt2 = 1f / sqrt(2f)
        val dir = getKeyboardDirection()
        isWalking = (dir != "  ")
        
        when(dir) {
            "W " -> body.setLinearVelocity(0f, maxSpeed)
            
            "S " -> body.setLinearVelocity(0f, -maxSpeed)
            
            "A " -> body.setLinearVelocity(-maxSpeed, 0f)
            
            "D " -> body.setLinearVelocity(maxSpeed, 0f)
            
            "WA" -> body.setLinearVelocity(-invsqrt2 * maxSpeed, invsqrt2 * maxSpeed)
            
            "WD" -> body.setLinearVelocity(invsqrt2 * maxSpeed, invsqrt2 * maxSpeed)
            
            "SA" -> body.setLinearVelocity(-invsqrt2 * maxSpeed, -invsqrt2 * maxSpeed)
            
            "SD" -> body.setLinearVelocity(invsqrt2 * maxSpeed, -invsqrt2 * maxSpeed)
            
            else -> body.setLinearVelocity(0f, 0f)
            
        }
        
        val vecToMouse = Vector2()
        when(dir) {
            "W " -> rotation = 90f
            
            "S " -> rotation = 270f
            
            "A " -> rotation = 180f
            
            "D " -> rotation = 0f
            
            "WA" -> rotation = 135f
            
            "WD" -> rotation = 45f
            
            "SA" -> rotation = 225f
            
            "SD" -> rotation = 315f
            
            else -> rotation = 360f - vecToMouse.set(input.x - graphics.width*0.5f, input.y - graphics.height*0.5f).nor().angle()
            
        }
        
        if(input.justTouched()) {
            //world.bulletSwarm.spawn(position.cpy().add(Vector2.X.setAngle(rotation).setLength(1f)), rotation)
            
        }
        
    }
    
    private fun getKeyboardDirection() : String {
        val dir = charArrayOf(' ', ' ')
        var primaryDirectionSet = false
        
        if(input.isKeyPressed(Input.Keys.W)) {
            dir[0] = 'W'
            primaryDirectionSet = true
            
        } else if(input.isKeyPressed(Input.Keys.S)) {
            dir[0] = 'S'
            primaryDirectionSet = true
            
        }
        
        if(input.isKeyPressed(Input.Keys.A)) {
            dir[if (primaryDirectionSet) 1 else 0] = 'A'
            
        } else if (input.isKeyPressed(Input.Keys.D)) {
            dir[if (primaryDirectionSet) 1 else 0] = 'D'
            
        }
        
        return String(dir)
        
    }
    
    override fun dispose() {
        //y tho
        
    }

}