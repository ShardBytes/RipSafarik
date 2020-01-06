package com.shardbytes.ripsafarik.game

import box2dLight.RayHandler
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import com.shardbytes.ripsafarik.actors.CollisionListener
import com.shardbytes.ripsafarik.blocks.*
import com.shardbytes.ripsafarik.components.technical.BlockCatalog
import com.shardbytes.ripsafarik.components.world.DaylightCycle
import com.shardbytes.ripsafarik.entity.Player
import com.shardbytes.ripsafarik.ui.Healthbar
import com.shardbytes.ripsafarik.ui.inventory.Hotbar
import com.shardbytes.ripsafarik.ui.inventory.PlayerInventory
import ktx.box2d.createWorld

object GameWorld : Disposable {

	val physics = createWorld()
	val lights = createLightHandler()
	val player = Player()

	init {
		//Set physics collider
		physics.setContactListener(CollisionListener())

		//Register all blocks
		BlockCatalog.registerBlock(Grass())
		BlockCatalog.registerBlock(Asfalt())
		BlockCatalog.registerBlock(Safarik())
		BlockCatalog.registerBlock(Concrete())
		BlockCatalog.registerBlock(Lamp())
		BlockCatalog.registerBlock(Wall())
		
		GameMap_new.loadMap("world")

		player.position.set(1f, 1f)

	}

	fun render(dt: Float, batch: SpriteBatch) {
		//Draw the world and everything
		GameMap_new.render(dt, batch)
		player.render(dt, batch)

	}

	fun renderUI(dt: Float, batch: SpriteBatch) {
		//Draw the UI
		Hotbar.render(batch)
		Healthbar.render(player.health.toInt(), batch)
		PlayerInventory.render(batch)

	}

	fun tick(dt: Float) {
		player.tick()
		GameMap_new.tick()
		DaylightCycle.tick(dt)

		physics.step(dt, 8, 3)

	}

	private fun createLightHandler(): RayHandler {
		RayHandler.setGammaCorrection(true)
		RayHandler.useDiffuseLight(true)

		val rayhandler = RayHandler(physics)

		rayhandler.setShadows(true)
		rayhandler.setAmbientLight(0.1f, 0.1f, 0.1f, 0.5f)
		rayhandler.setBlurNum(2)

		return rayhandler

	}

	override fun dispose() {
		player.dispose()
		physics.dispose()
		lights.dispose()

	}

}
