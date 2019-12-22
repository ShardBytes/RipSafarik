package com.shardbytes.ripsafarik.components.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector3
import com.shardbytes.ripsafarik.screens.GameScreen
import com.shardbytes.ripsafarik.ui.PlayerInventory

object InventoryInput : InputProcessor {

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
    override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false
    override fun keyTyped(character: Char): Boolean = false
    override fun keyUp(keycode: Int): Boolean = false
    override fun scrolled(amount: Int): Boolean = false
    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val screenCoords = GameScreen.uiCamera.innerCamera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))

        println(screenCoords)

        return false

    }

    override fun keyDown(keycode: Int): Boolean {
        checkCloseInventory(keycode)

        return false

    }

    private fun checkCloseInventory(keycode: Int) {
        if(keycode == Input.Keys.E) {
            closeInventory()

        }

    }

    private fun closeInventory() {
        PlayerInventory.isOpened = false
        Gdx.input.inputProcessor = InputCore

    }

}