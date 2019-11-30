package com.shardbytes.ripsafarik.entity

import com.badlogic.gdx.Gdx.graphics
import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils.radDeg
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.actors.GameMap
import com.shardbytes.ripsafarik.actors.GameWorld
import com.shardbytes.ripsafarik.assets.Animations
import com.shardbytes.ripsafarik.components.Entity
import com.shardbytes.ripsafarik.components.ItemInventory
import com.shardbytes.ripsafarik.ui.Healthbar
import ktx.box2d.body
import kotlin.math.min
import kotlin.math.sqrt

class Player() : Entity {
    
    private val width = 1f
    private val height = 1f
    private val maxSpeed = 4f //TODO: What unit?
    
    private var isWalking = false
    private var elapsedTime = 0f 
    private val animatedPlayer = Animations["animatedPlayer"]

    //Health stuff
    private val healthbar = Healthbar()
    private val maxHealth = 100f
    private var health = 100f
    private var regenSpeed = 1f
    
    val inventory: ItemInventory = ItemInventory()
    
    override val body = GameWorld.physics.body(BodyDef.BodyType.DynamicBody) {
        circle(radius = width*0.5f) { userData = this@Player }
        fixedRotation = true

    }
    
    override fun tick(dt: Float) {
        handleInput()

        //health regen
        health = min(maxHealth, health + regenSpeed)
        
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

        healthbar.render(health.toInt(), position, batch)
        
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
        
        if(input.isTouched) {
            val playerPos = position.cpy()
            val playerRotation = rotation
            val bullet = Bullet().apply {
                setPosition(playerPos.add(Vector2.X.rotate(rotation).setLength(0.65f)))
                body.linearVelocity = Vector2.X.setLength(30f).setAngle(playerRotation)

            }

            GameMap.Entities.spawn(bullet)

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

    /**
     * Damage the player. If health goes below zero, weelllll that sucks.
     */
    fun takeDamage(amount: Float) {
        health -= amount

        if(health <= 0) {
            System.exit(0)

        }

    }
    
    override fun dispose() {
        //y tho
        
    }

}