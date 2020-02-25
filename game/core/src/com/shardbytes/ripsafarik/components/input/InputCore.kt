package com.shardbytes.ripsafarik.components.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.MathUtils
import com.shardbytes.ripsafarik.blocks.Concrete
import com.shardbytes.ripsafarik.entity.ItemDrop
import com.shardbytes.ripsafarik.game.GameMap
import com.shardbytes.ripsafarik.game.GameWorld
import com.shardbytes.ripsafarik.game.MainGame
import com.shardbytes.ripsafarik.game.Settings
import com.shardbytes.ripsafarik.items.BlockItem
import com.shardbytes.ripsafarik.items.ItemStack
import com.shardbytes.ripsafarik.screens.GameScreen
import com.shardbytes.ripsafarik.screens.PauseScreen
import com.shardbytes.ripsafarik.ui.inventory.PlayerInventory

object InputCore : InputProcessor {

	var direction = 0b0000
	var newSelectedSlot = 1 //First slot, zero-th slot is invalid

	override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
	override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
	override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
	override fun mouseMoved(screenX: Int, screenY: Int): Boolean = false
	override fun keyTyped(character: Char): Boolean = false

	fun reset(): InputCore {
		direction = 0b0000
		return this

	}

	//Camera zooming
	override fun scrolled(amount: Int): Boolean {
		var newZoom = GameScreen.camera.getZoom() + 0.2f * amount

		if (newZoom >= 1f && newZoom <= Settings.CURRENT_ASPECT_RATIO) {
			newZoom = MathUtils.clamp(newZoom, 1f, Settings.CURRENT_ASPECT_RATIO)
			GameScreen.camera.setZoom(newZoom)

		}

		return true

	}

	//Stop moving in a direction when user releases a key
	override fun keyUp(keycode: Int): Boolean {
		turnOffDirection(keycode)

		return true

	}

	//Handle most of the keys
	override fun keyDown(keycode: Int): Boolean {
		turnOnDirection(keycode)
		selectInventorySlot(keycode)
		checkOpenInventory(keycode)
		checkEscapeKey(keycode)

		if(keycode == Input.Keys.H) {
			GameMap.spawn(ItemDrop(ItemStack(BlockItem(Concrete()), 10)).apply { createBody(); setPosition(GameWorld.player.position.cpy()) })
		}

		return true

	}

	private fun turnOnDirection(keycode: Int) {
		/*
		 *          1000               08
		 *      1010    1001         10  09
		 *  0010            0001   02      01       -> 1, 2, 4, 5, 6, 8, 9, 10 in decimal
		 *      0110    0101         06  05
		 *          0100               04
		 */
		when (keycode) {
			Input.Keys.W -> direction = direction or (1 shl 3)
			Input.Keys.S -> direction = direction or (1 shl 2)
			Input.Keys.A -> direction = direction or (1 shl 1)
			Input.Keys.D -> direction = direction or (1 shl 0)

		}

	}

	private fun turnOffDirection(keycode: Int) {
		when (keycode) {
			Input.Keys.W -> direction = direction and (1 shl 3).inv()
			Input.Keys.S -> direction = direction and (1 shl 2).inv()
			Input.Keys.A -> direction = direction and (1 shl 1).inv()
			Input.Keys.D -> direction = direction and (1 shl 0).inv()

		}

	}

	private fun selectInventorySlot(keycode: Int) {
		if (keycode in Input.Keys.NUM_1..Input.Keys.NUM_9) {
			newSelectedSlot = keycode - 7

		}

	}

	private fun checkOpenInventory(keycode: Int) {
		if (keycode == Input.Keys.E) {
			openInventory()

		}

	}

	private fun openInventory() {
		PlayerInventory.isOpened = true
		Gdx.input.inputProcessor = InventoryInput

		direction = 0b0000

	}

	private fun checkEscapeKey(keycode: Int) {
		if (keycode == Input.Keys.ESCAPE) {
			MainGame.screen = PauseScreen()

		}

	}

}