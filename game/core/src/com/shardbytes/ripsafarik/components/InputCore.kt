package com.shardbytes.ripsafarik.components

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.shardbytes.ripsafarik.screens.GameScreen

object InputCore: InputProcessor {

    var direction = 0b0000
    var newSelectedSlot = 1 //First slot, zero-th slot is invalid
    var inventoryOpened = false

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
    override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false
    override fun keyTyped(character: Char): Boolean = false
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

    //Camera zooming
    override fun scrolled(amount: Int): Boolean {
        GameScreen.camera.setZoom(GameScreen.camera.getZoom() + 0.2f * amount)

        return false

    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false

    }

    //Stop moving in a direction when user releases a key
    override fun keyUp(keycode: Int): Boolean {
        turnOffDirection(keycode)

        return false

    }

    //Handle most of the keys
    override fun keyDown(keycode: Int): Boolean {
        turnOnDirection(keycode)
        selectInventorySlot(keycode)
        checkOpenInventory(keycode)

        return false

    }

    private fun turnOnDirection(keycode: Int) {
        /*
         *          1000               08
         *      1010    1001         10  09
         *  0010            0001   02      01       -> 1, 2, 4, 5, 6, 8, 9, 10 in decimal
         *      0110    0101         06  05
         *          0100               04
         */
        when(keycode) {
            Input.Keys.W -> direction = direction or (1 shl 3)
            Input.Keys.S -> direction = direction or (1 shl 2)
            Input.Keys.A -> direction = direction or (1 shl 1)
            Input.Keys.D -> direction = direction or (1 shl 0)

        }

    }

    private fun turnOffDirection(keycode: Int) {
        when(keycode) {
            Input.Keys.W -> direction = direction and (1 shl 3).inv()
            Input.Keys.S -> direction = direction and (1 shl 2).inv()
            Input.Keys.A -> direction = direction and (1 shl 1).inv()
            Input.Keys.D -> direction = direction and (1 shl 0).inv()

        }

    }

    private fun selectInventorySlot(keycode: Int) {
        if(keycode in Input.Keys.NUM_1..Input.Keys.NUM_9) {
            newSelectedSlot = keycode - 7

        }

    }

    private fun checkOpenInventory(keycode: Int) {
        if(keycode == Input.Keys.E) {
            inventoryOpened = !inventoryOpened

            direction = 0b0000

        }

    }

}