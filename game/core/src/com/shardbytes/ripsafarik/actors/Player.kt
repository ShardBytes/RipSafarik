package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Gdx.graphics
import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils.radDeg
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.components.Entity
import com.shardbytes.ripsafarik.tools.GifDecoder
import ktx.box2d.body
import kotlin.math.sqrt

class Player(world: gameworld_old) : Entity {
    
    val WIDTH = 1f
    val HEIGHT = 1f
    val maxSpeed = 4f //What unit?
    
    private var isWalking = false
    private var elapsedTime = 0f 
    private val animatedPlayer = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("textures/entity/animatedPlayer.gif").read())
    
    override val body = world.physics.body(BodyDef.BodyType.DynamicBody) {
        circle(radius = WIDTH*0.5f)
        
    }
    
    override fun act(dt: Float) {
        handleInput()
        
    }
    
    override fun render(dt: Float, batch: SpriteBatch) {
        val originX = WIDTH * 0.5f
        val originY = HEIGHT * 0.5f
        val originBasedPositionX = position.x - originX
        val originBasedPositionY = position.y - originY

        if(isWalking) {
            batch.draw(animatedPlayer.getKeyFrame(elapsedTime), originBasedPositionX, originBasedPositionY, originX, originY, WIDTH, HEIGHT, 1f, 1f, body.angle * radDeg - 90f)

        } else {
            batch.draw(animatedPlayer.getKeyFrame(0.25f), originBasedPositionX, originBasedPositionY, originX, originY, WIDTH, HEIGHT, 1f, 1f, body.angle * radDeg - 90f)
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
            
            "WD" -> body.setLinearVelocity(invsqrt2 * maxSpeed, invsqrt2 *maxSpeed)
            
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
        var w_or_s = false
        
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            dir[0] = 'W'
            w_or_s = true
            
        } else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            dir[0] = 'S'
            w_or_s = true
            
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            dir[if (w_or_s) 1 else 0] = 'A'
            
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            dir[if (w_or_s) 1 else 0] = 'D'
            
        }
        
        return String(dir)
        
    }
    
    override fun dispose() {
        //y tho
        
    }
    
}