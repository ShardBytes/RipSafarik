package com.shardbytes.ripsafarik.entity

import com.badlogic.gdx.Gdx.graphics
import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input
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
import kotlin.math.sqrt

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
        input.inputProcessor = InputCore()
    }
    
    override fun tick(dt: Float) {
        //handleInput()
        //handleTouches()
        //handleNumbers()
        //handleKeys()

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

    private fun handleInput() {
        //No movement when inventory is opened
        if(!PlayerInventory.isOpened) {
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

        } else {
            //When player opens the inventory during walking, HALT
            //Also open the inventory but that's in separate method xd
            body.setLinearVelocity(0f, 0f)

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

    /**
     * The worst method you'll ever see.
     * Just because LibGDX has no input.getKeyPressed().
     * And I'm lazy to create a custom Input Processor just for this.
     */
    private fun handleNumbers() {
        if(!PlayerInventory.isOpened) {
            if(input.isKeyPressed(Input.Keys.NUM_1)) {
                if(Hotbar.hotbarSlots > 0) {
                    Hotbar.selectedSlot = 0

                }

            } else if(input.isKeyPressed(Input.Keys.NUM_2)) {
                if(Hotbar.hotbarSlots > 1) {
                    Hotbar.selectedSlot = 1

                }

            } else if(input.isKeyPressed(Input.Keys.NUM_3)) {
                if(Hotbar.hotbarSlots > 2) {
                    Hotbar.selectedSlot = 2

                }

            } else if(input.isKeyPressed(Input.Keys.NUM_4)) {
                if(Hotbar.hotbarSlots > 3) {
                    Hotbar.selectedSlot = 3

                }

            } else if(input.isKeyPressed(Input.Keys.NUM_5)) {
                if(Hotbar.hotbarSlots > 4) {
                    Hotbar.selectedSlot = 4

                }

            } else if(input.isKeyPressed(Input.Keys.NUM_6)) {
                if(Hotbar.hotbarSlots > 5) {
                    Hotbar.selectedSlot = 5

                }

            } else if(input.isKeyPressed(Input.Keys.NUM_7)) {
                if(Hotbar.hotbarSlots > 6) {
                    Hotbar.selectedSlot = 6

                }

            } else if(input.isKeyPressed(Input.Keys.NUM_8)) {
                if(Hotbar.hotbarSlots > 7) {
                    Hotbar.selectedSlot = 7

                }

            } else if(input.isKeyPressed(Input.Keys.NUM_9)) {
                if(Hotbar.hotbarSlots > 8) {
                    Hotbar.selectedSlot = 8

                }

            } else if(input.isKeyPressed(Input.Keys.NUM_0)) {
                if(Hotbar.hotbarSlots > 9) {
                    Hotbar.selectedSlot = 9

                }

            }

        }

    }

    private fun handleKeys() {
        if(input.isKeyJustPressed(Input.Keys.E)) {
            PlayerInventory.isOpened = !PlayerInventory.isOpened

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