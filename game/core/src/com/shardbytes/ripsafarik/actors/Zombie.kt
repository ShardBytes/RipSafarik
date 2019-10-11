package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.components.Entity
import com.shardbytes.ripsafarik.tools.GifDecoder
import ktx.box2d.body

class Zombie(private val world: gameworld_old,
             private val zombieType: ZombieType) : Entity {
    
    enum class ZombieType {
        NO_HAND,
        NO_HAND_BLOOD,
        HAND_BLOOD,
        RUNNER
    }
    
    val WIDTH = 1f
    val HEIGHT = 1f
    val knockbackForce = 100f
    private var maxSpeed = 0f
    private var rotationSpeed = 200f
    val vecToPlayer = Vector2()
    
    //Animation
    private var isWalking = false
    private var elapsedTime = 0f
    private val animatedMonster = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(getTextureFromType()).read())
    private var frames = 0
    private var frameTime = 0
    
    override val body = world.physics.body(BodyDef.BodyType.DynamicBody) {
        circle(radius = WIDTH*0.5f) {
            density = 10f
            friction = 1f
            userData = this@Zombie // store reference
        }
        linearDamping = 10f
    }
    
    private fun getTextureFromType() : String {
        return when(zombieType) {
            ZombieType.NO_HAND -> { frames = 4; frameTime = 150; maxSpeed = 1f; "textures/entity/animatedMonster.gif" }
            ZombieType.HAND_BLOOD -> { frames = 4; frameTime = 150; maxSpeed = 1f; "textures/entity/animatedMonster2.gif" }
            ZombieType.NO_HAND_BLOOD -> { frames = 4; frameTime = 150; maxSpeed = 1f; "textures/entity/animatedMonster3.gif" }
            ZombieType.RUNNER -> { frames = 4; frameTime = 100; maxSpeed = 3f; "textures/entity/animatedFastMonster.gif" }

        }
        
    }
    
    override fun act(dt: Float) {
        // TODO: follow player
        vecToPlayer.set(world.player.position.x - position.x, world.player.position.y - position.y).nor().setLength(80f)
        rotation = vecToPlayer.angle()
        body.applyForceToCenter(vecToPlayer, true)
    }
    
    override fun render(dt: Float, batch: SpriteBatch) {
        val originX = WIDTH * 0.5f
        val originY = HEIGHT * 0.5f
        val originBasedPositionX = position.x - originX
        val originBasedPositionY = position.y - originY

        if(isWalking) {
            batch.draw(animatedMonster.getKeyFrame(elapsedTime), originBasedPositionX, originBasedPositionY, originX, originY, WIDTH, HEIGHT, 1f, 1f, body.angle * MathUtils.radDeg - 90f)

        } else {
            batch.draw(animatedMonster.getKeyFrame(0.25f), originBasedPositionX, originBasedPositionY, originX, originY,WIDTH, HEIGHT, 1f, 1f, body.angle * MathUtils.radDeg - 90f)

        }
        elapsedTime += dt
        elapsedTime %= frames * frameTime * 0.001f
        
    }
    
    
    override fun dispose() {}

}