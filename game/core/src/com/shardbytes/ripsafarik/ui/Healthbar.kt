package com.shardbytes.ripsafarik.ui

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

class Healthbar {

    var width = 100
    var height = 5

    fun render(healthValue: Int, position: Vector2, batch: SpriteBatch) {
        val pixels = Pixmap(width, height, Pixmap.Format.RGBA8888)
        pixels.setColor(1f, 0f, 0f, 1f)
        pixels.fillRectangle(0, 0, healthValue, height)

        pixels.setColor(0.15f, 0.15f, 0.15f, 1f)
        pixels.fillRectangle(healthValue, 0, width - healthValue, height)

        batch.draw(TextureRegion(Texture(pixels)), position.x - 1f, position.y + 0.5f, 0.5f, 0.5f, 2f, 0.05f, 1f, 1f, 0f)

    }

}