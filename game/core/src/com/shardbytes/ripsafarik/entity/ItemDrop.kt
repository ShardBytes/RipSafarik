package com.shardbytes.ripsafarik.entity

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

    override val body: Body = GameWorld.physics.body(BodyDef.BodyType.StaticBody)

    override fun render(dt: Float, batch: SpriteBatch) {
        val originX = 0.5f
        val originY = 0.5f
        val originBasedPositionX = position.x - originX
        val originBasedPositionY = position.y - originY

        batch.draw(TextureRegion(item.texture), originBasedPositionX, originBasedPositionY, originX, originY, 1.0f, 1.0f, 0.33f, 0.33f, body.angle * MathUtils.radDeg - 90f)

    }

    override fun tick(dt: Float) {
        currentLifetime += dt

        if(currentLifetime > maximumLifetime) {
            GameMap.Entities.despawn(this)

        }

    }

    override fun dispose() {
        //y tho
    }

}