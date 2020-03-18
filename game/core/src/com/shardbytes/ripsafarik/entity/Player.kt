package com.shardbytes.ripsafarik.entity

import com.badlogic.gdx.Gdx.graphics
import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.assets.Animations
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.IModifiable
import com.shardbytes.ripsafarik.components.IUsable
import com.shardbytes.ripsafarik.components.input.InputCore
import com.shardbytes.ripsafarik.components.world.Entity
import com.shardbytes.ripsafarik.items.ItemStack
import com.shardbytes.ripsafarik.ui.inventory.Hotbar
import com.shardbytes.ripsafarik.ui.inventory.PlayerInventory
import ktx.box2d.BodyDefinition
import kotlin.math.min
import kotlin.system.exitProcess

class Player : Entity() {

    private val width = 1f
    private val height = 2f
    private val maxSpeed = 4f //TODO: What unit?

    private var isWalking = false
    private var elapsedTime = 0f

    private val animationUp = Animations["playerUp"]
    private val animationDown = Animations["playerDown"]
    private val animationLeft = Animations["playerLeft"]
    private val animationRight = Animations["playerRight"]
    private val staticUp = TextureRegion(Textures.Entity["playerStatic/up"])
    private val staticDown = TextureRegion(Textures.Entity["playerStatic/down"])
    private val staticLeft = TextureRegion(Textures.Entity["playerStatic/left"])
    private val staticRight = TextureRegion(Textures.Entity["playerStatic/right"])

    //Health stuff
    override var maxHealth = 100f
    override var health = 100f
    override var regenSpeed = 1f

    //Item using
    private var itemUseCooldown = 0f
    private var elapsedItemUseCooldown = 0f

    override val bodyType = BodyDef.BodyType.DynamicBody
    override val bodyDef: BodyDefinition.() -> Unit = {
        circle(radius = width * 0.4f) { userData = this@Player }
        fixedRotation = true

    }

    override fun tick() {
        handleMovement()
        handleTouches()
        handleNumbers()

        //health regen
        health = min(maxHealth, health + regenSpeed)

        //item use cooldown
        elapsedItemUseCooldown = min(itemUseCooldown, elapsedItemUseCooldown + 1f / 50f)

    }

    override fun render(dt: Float, batch: SpriteBatch) {
        val posX = position.x - width * 0.5f
        val posY = position.y - height * 0.2f

        if(isWalking) {
            when(body.angle * MathUtils.radDeg) {
                in 0f..45f -> batch.draw(animationRight.getKeyFrame(elapsedTime), posX, posY, 0f, 0f, width, height, 1f, 1f, 0f)
                in 45f..135f -> batch.draw(animationUp.getKeyFrame(elapsedTime), posX, posY, 0f, 0f, width, height, 1f, 1f, 0f)
                in 135f..225f -> batch.draw(animationLeft.getKeyFrame(elapsedTime), posX, posY, 0f, 0f, width, height, 1f, 1f, 0f)
                in 225f..315f -> batch.draw(animationDown.getKeyFrame(elapsedTime), posX, posY, 0f, 0f, width, height, 1f, 1f, 0f)
                in 315f..360f -> batch.draw(animationRight.getKeyFrame(elapsedTime), posX, posY, 0f, 0f, width, height, 1f, 1f, 0f)

            }

        } else {
            when(body.angle * MathUtils.radDeg) {
                in 0f..45f -> batch.draw(staticRight, posX, posY, 0f, 0f, width, height, 1f, 1f, 0f)
                in 45f..135f -> batch.draw(staticUp, posX, posY, 0f, 0f, width, height, 1f, 1f, 0f)
                in 135f..225f -> batch.draw(staticLeft, posX, posY, 0f, 0f, width, height, 1f, 1f, 0f)
                in 225f..315f -> batch.draw(staticDown, posX, posY, 0f, 0f, width, height, 1f, 1f, 0f)
                in 315f..360f -> batch.draw(staticRight, posX, posY, 0f, 0f, width, height, 1f, 1f, 0f)

            }

        }

        elapsedTime += dt
        elapsedTime %= 0.8f //4 animation frames @ 200ms per frame rate

    }

    private fun handleMovement() {
        if (!PlayerInventory.isOpened) {
            val dir = InputCore.direction
            isWalking = dir > 0

            val mouseAngle = 360f - Vector2(input.x - graphics.width * 0.5f, input.y - graphics.height * 0.5f).nor().angle()
            val movementVector = Vector2((dir shr 0 and 1) * maxSpeed, (dir shr 3 and 1) * maxSpeed)
                    .sub((dir shr 1 and 1) * maxSpeed, (dir shr 2 and 1) * maxSpeed)
                    .setLength(maxSpeed)

            rotation = if (dir > 0) movementVector.angle() else mouseAngle
            body.linearVelocity = movementVector

        } else {
            body.linearVelocity = Vector2()

        }

    }

    private fun handleTouches() {
        if (!PlayerInventory.isOpened) {
            if (input.isTouched) {
                if (elapsedItemUseCooldown == itemUseCooldown) {
                    val item = Hotbar.hotbarSlots[Hotbar.selectedSlot].itemStack?.item
                    if (item is IUsable) { //item can be null, but null is not IUsable, so good
                        item.use(this)
                        itemUseCooldown = item.cooldown
                        elapsedItemUseCooldown = 0f

                    }

                }

            }

        }

    }

    fun modifyHeldItem(diff: Int) {
        if (!PlayerInventory.isOpened) {
            val item = Hotbar.hotbarSlots[Hotbar.selectedSlot].itemStack?.item
            if (item is IModifiable) {
                item.modify(diff)
                if (item is IUsable) {
                    itemUseCooldown = item.cooldown
                    elapsedItemUseCooldown = 0f

                }

            }

        }

    }

    private fun handleNumbers() {
        if (!PlayerInventory.isOpened) {
            val newSlot = InputCore.newSelectedSlot.dec()

            if (Hotbar.slotCount > newSlot) {
                Hotbar.selectedSlot = newSlot

            }

        }

    }

    /**
     * Damage the player. If health goes below zero, weelllll that sucks.
     */
    fun takeDamage(amount: Float) {
        health -= amount

        if (health <= 0) {
            exitProcess(0)

        }

    }

    //TODO: max stack size
    fun pickUp(itemStack: ItemStack): Boolean {
        //Try to stack them together
        var emptySlot = Hotbar.hotbarSlots.find { itemStack.hasTheSameItemAs(it.itemStack) }
        if (emptySlot == null) {
            emptySlot = PlayerInventory.slots.find { itemStack.hasTheSameItemAs(it.itemStack) }

        }
        if (emptySlot != null) {
            emptySlot.itemStack!!.amount += itemStack.amount
            return true

        }

        //Try to find an empty slot if there is no such stack in the inventory
        emptySlot = Hotbar.hotbarSlots.find { it.itemStack == null }
        if (emptySlot == null) {
            emptySlot = PlayerInventory.slots.find { it.itemStack == null }

        }
        if (emptySlot == null) {
            return false

        }
        emptySlot.itemStack = itemStack
        return true

    }

    override fun dispose() {
        //y tho

    }

}