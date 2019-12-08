package com.shardbytes.ripsafarik.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.actors.GameMap
import com.shardbytes.ripsafarik.actors.GameWorld
import com.shardbytes.ripsafarik.components.Entity
import com.shardbytes.ripsafarik.components.Item
import ktx.box2d.body

class ItemDrop(private val item: Item) : Entity {

    private val maximumLifetime = 10f //seconds before despawn
    private var currentLifetime = 0f

    private val font = BitmapFont().apply { data.setScale(0.1f) } //TODO: font scale fucks shit up, fix asap
    private var availableForPickup = false

    override val body: Body = GameWorld.physics.body(BodyDef.BodyType.StaticBody)

    override fun render(dt: Float, batch: SpriteBatch) {
        val originX = 0.5f
        val originY = 0.5f
        val originBasedPositionX = position.x - originX //TODO: redo naming because originX and originY mean something else
        val originBasedPositionY = position.y - originY

        //Draw the item
        batch.draw(TextureRegion(item.texture), originBasedPositionX, originBasedPositionY, originX, originY, 1.0f, 1.0f, 1f, 1f, body.angle * MathUtils.radDeg - 90f)

        //Pickup notice
        if(availableForPickup) {
            font.draw(batch, "Press F to pick up", position.x, position.y)

        }

    }

    override fun tick(dt: Float) {
        //If close enough to the player, set available for picking up
        availableForPickup = position.dst(GameWorld.player.position) < 1

        //And actually try to pick up, if key is pressed
        if(availableForPickup) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                val successful = GameWorld.player.inventory.pickUpItem(item)
                if(successful) {
                    GameMap.Entities.despawn(this)

                }

            }

        }

        //Despawn logic
        currentLifetime += dt

        if(currentLifetime > maximumLifetime) {
            GameMap.Entities.despawn(this)

        }

    }

    override fun dispose() {
        //y tho
    }

}