package com.shardbytes.ripsafarik.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.actors.GameMap
import com.shardbytes.ripsafarik.actors.GameWorld
import com.shardbytes.ripsafarik.assets.Animations
import com.shardbytes.ripsafarik.components.Entity
import ktx.box2d.body
import kotlin.math.min

class Zombie(private var world: GameWorld,
             private var zombieType: ZombieType) : Entity {
    
    enum class ZombieType {
        NO_HAND,
        NO_HAND_BLOOD,
        HAND_BLOOD,
        RUNNER;

    }
    
    val WIDTH = 1f
    val HEIGHT = 1f
    private var maxSpeed = 0f
    private var followRange = 0f
    var knockbackForce = 0f

    //Health stuff
    private val maxHealth = 100f
    private var health = 100f
    private var regenSpeed = 1f
    
    //Animation
    private var isWalking = false
    private val animatedMonster = Animations[getTextureFromType()]

    //Animation timings
    private var elapsedTime = 0f
    private var frames = 0
    private var frameTime = 0
    
    override val body = world.physics.body(BodyDef.BodyType.DynamicBody) {
        circle(radius = WIDTH*0.5f) { userData = this@Zombie }

    }
    
    private fun getTextureFromType() : String {
        //TODO: fix walking animation
        return when(zombieType) {
            ZombieType.NO_HAND -> { frames = 4; frameTime = 150; maxSpeed = 1f; followRange = 10f; knockbackForce = 20f; "animatedMonster" }
            ZombieType.HAND_BLOOD -> { frames = 4; frameTime = 150; maxSpeed = 1f; followRange = 10f; knockbackForce = 20f; "animatedMonster2" }
            ZombieType.NO_HAND_BLOOD -> { frames = 4; frameTime = 150; maxSpeed = 1f; followRange = 10f; knockbackForce = 20f; "animatedMonster3" }
            ZombieType.RUNNER -> { frames = 4; frameTime = 100; maxSpeed = 3f; followRange = 10f; knockbackForce = 50f; "animatedFastMonster" }

        }
        
    }
    
    override fun tick(dt: Float) {
        //vecToPlayer.set(world.player.position.x - position.x, world.player.position.y - position.y).nor().setLength(80f)
        //rotation = vecToPlayer.angle()
        //body.applyForceToCenter(vecToPlayer, true)

        //if zombie is in the follow radius, follow the player (well obviously smh)
        val isInFollowRadius = isZombieInPlayerFollowRadius()
        if(isInFollowRadius) {
            val rotationVector = Vector2(world.player.position.x - position.x, world.player.position.y - position.y).nor()
            rotation = rotationVector.angle()
            body.setLinearVelocity(rotationVector.setLength(maxSpeed))

        } else {
            body.setLinearVelocity(0.0f, 0.0f)

        }

        //set animation to walking
        isWalking = isInFollowRadius

        //TODO: implement random zombie behaviour

        //health regen
        health = min(maxHealth, health + regenSpeed)

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

    /**
     * Damage the Zombie. If health goes below zero, despawn.
     */
    fun takeDamage(amount: Float) {
        health -= amount

        if(health <= 0) {
            GameMap.Entities.despawn(this)

        }

    }

    fun isZombieInPlayerFollowRadius(): Boolean {
        return position.dst2(world.player.position) < (followRange * followRange)

    }

}