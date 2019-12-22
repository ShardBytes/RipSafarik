package com.shardbytes.ripsafarik.ui

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import com.shardbytes.ripsafarik.Settings

object Healthbar: Disposable {

    var width = 100
    var height = 1

    private val textures = arrayOfNulls<TextureRegion>(101)

    init {
        for(i in 0..100) {
            textures[i] = getTexture(i)

        }

    }

    private fun getTexture(healthValue: Int): TextureRegion {
        val pixels = Pixmap(width, height, Pixmap.Format.RGBA8888)
        pixels.setColor(1f, 0f, 0f, 1f)
        pixels.fillRectangle(0, 0, healthValue, height)

        pixels.setColor(0.15f, 0.15f, 0.15f, 1f)
        pixels.fillRectangle(healthValue, 0, width - healthValue, height)

        val texture = TextureRegion(Texture(pixels))
        pixels.dispose()

        return texture

    }

    fun render(healthValue: Int, batch: SpriteBatch) {
        val pixels = getTexture(healthValue)

        val screenWidth = Settings.GAME_V_WIDTH * Settings.CURRENT_ASPECT_RATIO
        val screenHeight = Settings.GAME_V_HEIGHT / Settings.CURRENT_ASPECT_RATIO

        batch.draw(pixels,
                -5f, //half the width of the healthbar
                screenHeight * -0.5f + 1.1f, //at the bottom + 1.1 points to the top (1 down is the hotbar)
                0.5f,
                0.5f,
                10f,
                0.3f,
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