package com.shardbytes.ripsafarik.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.game.GameMap
import com.shardbytes.ripsafarik.screens.GameScreen

object DebugView {

    fun render(batch: SpriteBatch) {
        Textures.Font.bitmapFont.draw(batch, "CX: ${GameMap.tickedChunks.size}\nCQ: ${GameMap.chunkTickQueue.size}", GameScreen.textCamera.viewport!!.screenWidth.toFloat() / -2.1f, GameScreen.textCamera.viewport!!.screenHeight.toFloat() / 3)

    }

}