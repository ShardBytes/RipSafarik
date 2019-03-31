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
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.shardbytes.ripsafarik.components.Entity
import com.shardbytes.ripsafarik.tools.GifDecoder
import ktx.box2d.body

class Player(private val world: GameWorld) : Entity {
    
    val WIDTH = 1f
    val HEIGHT = 1f
    var health = 100f
    val vecToMouse = Vector2()
    
    // animation
    private var isWalking = false
    private var elapsedTime = 0f
    private val animatedPlayer = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("textures/entity/animatedPlayer.gif").read())
    
    override val body = world.physics.body(BodyDef.BodyType.DynamicBody) {
        circle(radius = WIDTH*0.5f) {
            density = 10f
            friction = 0f
            userData = this@Player // store reference to body
        }
        linearDamping = 10f
    }
    
    override fun act(dt: Float) {
        handleInput(dt)
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

    private fun handleInput(dt: Float) {
        val sped = 4f
        //Movement
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.linearDamping = 0f
            isWalking = true
            body.setLinearVelocity(0f, sped)
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.linearDamping = 0f
            isWalking = true
            body.setLinearVelocity(0f, -sped)
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.linearDamping = 0f
            isWalking = true
            body.setLinearVelocity(-sped, 0f)
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.linearDamping = 0f
            isWalking = true
            body.setLinearVelocity(sped, 0f)
        } else {
            isWalking = false
            body.linearDamping = 10f
        }
        
        vecToMouse.set(input.x - graphics.width*0.5f, input.y - graphics.height*0.5f).nor()
        rotation = 360f - vecToMouse.angle()
        
        if (input.isKeyPressed(Input.Keys.Q)) {
            body.setLinearVelocity(0f, 0f)
        }
        
        if (input.justTouched()) {
        
        }
        
        
    }
    
    override fun dispose() {
    
    }
    
    
}