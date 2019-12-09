package com.shardbytes.ripsafarik.entity

import com.badlogic.gdx.Gdx.graphics
import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils.radDeg
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.actors.GameWorld
import com.shardbytes.ripsafarik.assets.Animations
import com.shardbytes.ripsafarik.components.Entity
import com.shardbytes.ripsafarik.components.IUsable
import com.shardbytes.ripsafarik.components.InputCore
import com.shardbytes.ripsafarik.components.ItemInventory
import com.shardbytes.ripsafarik.ui.Hotbar
import com.shardbytes.ripsafarik.ui.PlayerInventory
import ktx.box2d.body
import kotlin.math.min

class Player() : Entity {
    
    private val width = 1f
    private val height = 1f
    private val maxSpeed = 4f //TODO: What unit?
    
    private var isWalking = false
    private var elapsedTime = 0f 
    private val animatedPlayer = Animations["animatedPlayer"]

    //Health stuff
    val maxHealth = 100f
    var health = 100f
    var regenSpeed = 1f
    
    val inventory: ItemInventory = ItemInventory()
    
    override val body = GameWorld.physics.body(BodyDef.BodyType.DynamicBody) {
        circle(radius = width*0.5f) { userData = this@Player }
        fixedRotation = true

    }

    init {
        input.inputProcessor = InputCore
    }
    
    override fun tick(dt: Float) {
        handleMovement()
        handleTouches()
        handleNumbers()
        handleKeys()

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
        
    }

    private fun handleMovement() {
        if(!PlayerInventory.isOpened) {
            val dir = InputCore.direction
            isWalking = dir > 0

            val mouseAngle = 360f - Vector2(input.x - graphics.width*0.5f, input.y - graphics.height*0.5f).nor().angle()
            val movementVector = Vector2((dir shr 0 and 1) * maxSpeed, (dir shr 3 and 1) * maxSpeed)
                    .sub((dir shr 1 and 1) * maxSpeed, (dir shr 2 and 1) * maxSpeed)
                    .setLength(maxSpeed)

            rotation = if (dir > 0) { movementVector.angle() } else { mouseAngle }
            body.setLinearVelocity(movementVector)

        }

    }

    private fun handleTouches() {
        if(!PlayerInventory.isOpened) {
            if (input.isTouched) {
                val item = inventory.hotbar[Hotbar.selectedSlot]
                if (item is IUsable) {
                    item.use(this)

                }

            }

        }

    }

    private fun handleNumbers() {
        if(!PlayerInventory.isOpened) {
            val newSlot = InputCore.newSelectedSlot.dec()

            if(Hotbar.hotbarSlots > newSlot) {
                Hotbar.selectedSlot = newSlot

            }

        }

    }

    private fun handleKeys() {
        PlayerInventory.isOpened = InputCore.inventoryOpened

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