package com.shardbytes.ripsafarik.components.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.shardbytes.ripsafarik.Settings
import com.shardbytes.ripsafarik.screens.GameScreen
import com.shardbytes.ripsafarik.ui.inventory.Hotbar
import com.shardbytes.ripsafarik.ui.inventory.PlayerInventory

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
        slotClicked(screenCoords)

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
    
    private fun slotClicked(atCoords: Vector3) { 
        //check inventory slots
        var slot = PlayerInventory.slots.find { it.isCoordinateInsideSlot(Vector2(atCoords.x, atCoords.y)) }
        
        //check hotbar slots
        if(slot == null) {
            slot = Hotbar.hotbarSlots.find { it.isCoordinateInsideSlot(Vector2(atCoords.x, atCoords.y)) }
        }
        
        if(slot != null) {
            slot.clicked()
            
        }
        
    }

}