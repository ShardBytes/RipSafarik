package com.shardbytes.ripsafarik.entity

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.actors.GameMap
import com.shardbytes.ripsafarik.actors.GameWorld
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.Entity
import ktx.box2d.body

class Bullet : Entity {

    private val texture = TextureRegion(Textures.Entity["bullet"])
    private val width = 0.1f
    private val height = 0.1f

    private val maximumLifetime = 3f //seconds before despawn
    private var currentLifetime = 0f

    override val body: Body = GameWorld.physics.body(BodyDef.BodyType.DynamicBody) {
        circle(0.1f) { userData = this@Bullet }

    }

    override fun render(dt: Float, batch: SpriteBatch) {
        val originX = width * 0.5f
        val originY = height * 0.5f
        val originBasedPositionX = position.x - originX
        val originBasedPositionY = position.y - originY

        batch.draw(texture, originBasedPositionX, originBasedPositionY, originX, originY, width, height, 1f, 1f, body.angle * MathUtils.radDeg - 90f)

    }

    override fun tick(dt: Float) {
        currentLifetime += dt
        if(currentLifetime > maximumLifetime) {
            GameMap.Entities.despawn(this)

        }

    }

    override fun dispose() {}

}