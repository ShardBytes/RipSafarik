package com.shardbytes.ripsafarik.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.game.GameMap
import com.shardbytes.ripsafarik.game.Settings

object DebugView {

    fun render(batch: SpriteBatch) {
        Textures.Font.bitmapFont.draw(batch, "CX: ${GameMap.tickedChunks.size}\nCQ: ${GameMap.chunkTickQueue.size}", -400f, 200f / Settings.CURRENT_ASPECT_RATIO)

    }

}