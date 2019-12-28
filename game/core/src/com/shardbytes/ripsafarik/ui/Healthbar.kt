package com.shardbytes.ripsafarik.ui

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import com.shardbytes.ripsafarik.Settings

object Healthbar: Disposable {

    val width = 8f
    val height = 0.1f

    private val textures = arrayOfNulls<TextureRegion>(101)

    init {
        for(i in 0..100) {
            textures[i] = getTexture(i)

        }

    }

    private fun getTexture(healthValue: Int): TextureRegion {
        val pixels = Pixmap(100, 1, Pixmap.Format.RGBA8888)
        pixels.setColor(1f, 0f, 0f, 1f)
        pixels.fillRectangle(0, 0, healthValue, 1)

        pixels.setColor(0.15f, 0.15f, 0.15f, 1f)
        pixels.fillRectangle(healthValue, 0, 100 - healthValue, 1)

        val texture = TextureRegion(Texture(pixels))
        pixels.dispose()

        return texture

    }

    fun render(healthValue: Int, batch: SpriteBatch) {
        val pixels = getTexture(healthValue)

        batch.draw(pixels,
                -width * 0.5f, //half the width of the healthbar
                Settings.VISIBLE_SCREEN_HEIGHT_GUI * -0.5f + 1.1f, //at the bottom + 1.1 points to the top (1 down is the hotbar)
                0.5f,
                0.5f,
                width,
                height,
                1f,
                1f,
                0f)


    }

    override fun dispose() {
        textures.forEach {
            it!!.texture.dispose()

        }

    }

    operator fun get(int: Int) = textures[int]

}