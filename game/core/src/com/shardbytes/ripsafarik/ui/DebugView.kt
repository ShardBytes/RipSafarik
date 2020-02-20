package com.shardbytes.ripsafarik.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.game.GameMap_new
import com.shardbytes.ripsafarik.game.Settings

object DebugView {

    fun render(batch: SpriteBatch) {
        Textures.Font.bitmapFont.draw(batch, "CX: ${GameMap_new.chunks.size}", -7.5f, 7f / Settings.CURRENT_ASPECT_RATIO)

    }

}