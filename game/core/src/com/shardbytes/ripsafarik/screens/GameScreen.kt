package com.shardbytes.ripsafarik.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.shardbytes.ripsafarik.actors.Camera
import com.shardbytes.ripsafarik.blocks.EmptyBlock
import com.shardbytes.ripsafarik.components.technical.BlockCatalog
import com.shardbytes.ripsafarik.game.GameWorld
import com.shardbytes.ripsafarik.game.Settings
import com.shardbytes.ripsafarik.items.BlockItem
import com.shardbytes.ripsafarik.items.DestroyTool
import com.shardbytes.ripsafarik.items.Flashlight
import com.shardbytes.ripsafarik.items.ItemStack
import com.shardbytes.ripsafarik.ui.Healthbar
import com.shardbytes.ripsafarik.ui.inventory.Hotbar
import kotlin.concurrent.thread

object GameScreen : Screen {

	// rendering
	var camera = Camera(Settings.GAME_V_WIDTH, Settings.GAME_V_HEIGHT, true)
	var uiCamera = Camera(Settings.GAME_V_WIDTH, Settings.GAME_V_HEIGHT, false)
	var batch = SpriteBatch()

	// world
	val world = GameWorld
	val debugRenderer = Box2DDebugRenderer()

	init {
		camera.lockOn(world.player)

		thread(true, true) {
			while (true) {
				val enteredString = readLine()
				if (enteredString?.startsWith("-giveBlock ") == true) {
					val blockId = enteredString.substringAfter(" ").substringBefore(" ")
					val amount = enteredString.substringAfterLast(" ").toIntOrNull()
					
					val block = BlockCatalog.getBlock(blockId)
					if(block.name == "default") {
						System.err.println("Unknown block.")
						
					} else {
						println("Given $amount $blockId to player.")
						world.player.pickUp(ItemStack(BlockItem(block), amount ?: 1))
						
					}

				} else if (enteredString == "-giveDestroyTool") {
					println("Given 1 destroyTool to player.")
					world.player.pickUp(ItemStack(DestroyTool(), 1))

				} else if(enteredString == "-giveFlashlight") {
					println("Given 1 flashlight to player.")
					world.player.pickUp(ItemStack(Flashlight(), 1))
				} else if(enteredString == "-help"){
					println("Commands:\n" +
							"-giveBlock {blockId} [amount]\n" +
							"-giveDestroyTool\n" +
							"-giveFlashlight\n")
					
				} else {
					System.err.println("Invalid command. Check -help.")
					
				}

			}

		}

	}

	/*
	==== update oredr ====
	It's easiest to think of your order in a single frame, think of it as a series of dependencies.

	User input depends on nothing, so it goes first.
	Objects being updated depend on the user input, so they go second.
	Physics depend on the new updated objects, so it goes third.
	Rendering depends on the latest physics state and object updates, so it goes fourth.
	UI depends on the scene to already be rendered, so it goes fifth.
	 */
	override fun render(dt: Float) {
		// tick the world first
		world.tick(dt)

		// update camera before rendering
		camera.update()
		batch.projectionMatrix.set(camera.innerCamera.combined)

		// clear screen
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		// render stuff
		batch.begin()
		world.render(dt, batch)
		batch.end()

		// debug render world physics into camera matrix
		if (Settings.PHYSICS_DEBUG_ACTIVE) debugRenderer.render(world.physics, camera.innerCamera.combined)

		//Lighting
		GameWorld.lights.useCustomViewport(camera.viewport!!.screenX, camera.viewport!!.screenY, camera.viewport!!.screenWidth, camera.viewport!!.screenHeight)
		GameWorld.lights.setCombinedMatrix(camera.innerCamera)
		GameWorld.lights.updateAndRender()

		// render hud at the top
		uiCamera.update()
		batch.projectionMatrix.set(uiCamera.innerCamera.projection)
		batch.begin()
		world.renderUI(dt, batch)
		batch.end()

	}

	override fun show() {

	}

	override fun pause() {

	}

	override fun resume() {

	}

	override fun resize(width: Int, height: Int) {
		camera.windowResized(width, height)
		uiCamera.windowResized(width, height)

		Settings.CURRENT_ASPECT_RATIO = width.toFloat() / height.toFloat()
		Hotbar.updateSlotPositions()

	}

	override fun dispose() {
		debugRenderer.dispose()
		batch.dispose()
		world.dispose()

		Healthbar.dispose()

	}

	override fun hide() {

	}

}
