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
import com.shardbytes.ripsafarik.components.Item
import com.shardbytes.ripsafarik.components.input.InputCore
import com.shardbytes.ripsafarik.ui.inventory.Hotbar
import com.shardbytes.ripsafarik.ui.inventory.PlayerInventory
import ktx.box2d.body
import kotlin.math.min
import kotlin.system.exitProcess

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

    //Item using
    var itemUseCooldown = 0f
    var elapsedItemUseCooldown = 0f
    
    override val body = GameWorld.physics.body(BodyDef.BodyType.DynamicBody) {
        circle(radius = width*0.5f) { userData = this@Player }
        fixedRotation = true

    }
    
    override fun tick(dt: Float) {
        handleMovement()
        handleTouches()
        handleNumbers()

        //health regen
        health = min(maxHealth, health + regenSpeed)

        //item use cooldown
        elapsedItemUseCooldown = min(itemUseCooldown, elapsedItemUseCooldown + dt)
        
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
            body.linearVelocity = movementVector

        } else {
            body.linearVelocity = Vector2()

        }

    }

    private fun handleTouches() {
        if(!PlayerInventory.isOpened) {
            if (input.isTouched) {
                if(elapsedItemUseCooldown == itemUseCooldown) {
                    val item = Hotbar.hotbarSlots[Hotbar.selectedSlot].item
                    if (item is IUsable) { //item can be null, but null is not IUsable, so good
                        item.use(this)
                        itemUseCooldown = item.cooldown
                        elapsedItemUseCooldown = 0f

                    }

                }

            }

        }

    }

    private fun handleNumbers() {
        if(!PlayerInventory.isOpened) {
            val newSlot = InputCore.newSelectedSlot.dec()

            if(Hotbar.slotCount > newSlot) {
                Hotbar.selectedSlot = newSlot

            }

        }

    }

    /**
     * Damage the player. If health goes below zero, weelllll that sucks.
     */
    fun takeDamage(amount: Float) {
        health -= amount

        if(health <= 0) {
            exitProcess(0)

        }

    }
    
    fun pickUp(item: Item): Boolean {
        var emptySlot = Hotbar.hotbarSlots.find { it.item == null }
        if(emptySlot == null) {
            emptySlot = PlayerInventory.slots.find { it.item == null }
            
        }
        if(emptySlot == null) {
            return false
            
        }
        emptySlot.item = item
        return true
        
    }
    
    override fun dispose() {
        //y tho
        
    }

}