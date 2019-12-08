package com.shardbytes.ripsafarik.components

import com.badlogic.gdx.InputProcessor
import com.shardbytes.ripsafarik.screens.GameScreen

class InputCore: InputProcessor {

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
    override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false
    override fun keyTyped(character: Char): Boolean = false
    override fun keyUp(keycode: Int): Boolean = false
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

    override fun scrolled(amount: Int): Boolean {
        GameScreen.camera.setZoom(GameScreen.camera.getZoom() + 0.2f * amount)

        return false

    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false

    }

    override fun keyDown(keycode: Int): Boolean {
        return false

    }

}