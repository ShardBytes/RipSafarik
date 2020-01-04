package com.shardbytes.ripsafarik.game

import box2dLight.RayHandler
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.shardbytes.ripsafarik.actors.CollisionListener
import com.shardbytes.ripsafarik.blocks.*
import com.shardbytes.ripsafarik.components.technical.BlockCatalog
import com.shardbytes.ripsafarik.components.world.DaylightCycle
import com.shardbytes.ripsafarik.entity.Player
import com.shardbytes.ripsafarik.entity.zombie.ZombieNoHand
import com.shardbytes.ripsafarik.entity.zombie.ZombieNoHandWithBlood
import com.shardbytes.ripsafarik.entity.zombie.ZombieRunner
import com.shardbytes.ripsafarik.entity.zombie.ZombieWithHandWithBlood
import com.shardbytes.ripsafarik.tools.SaveManager
import com.shardbytes.ripsafarik.ui.Healthbar
import com.shardbytes.ripsafarik.ui.inventory.Hotbar
import com.shardbytes.ripsafarik.ui.inventory.PlayerInventory
import ktx.box2d.createWorld

object GameWorld : Disposable {

	val physics = createWorld()
	val lights = createLightHandler()
	val player = Player()

	var load = false

	init {
		//Set physics collider
		physics.setContactListener(CollisionListener())

		//Register all blocks
		BlockCatalog.registerBlock(Grass())
		BlockCatalog.registerBlock(Asfalt())
		BlockCatalog.registerBlock(Safarik())
		BlockCatalog.registerBlock(Concrete())
		BlockCatalog.registerBlock(Lamp())

		GameMap.loadAll("world")

		if(load){
			//player.position.set(SaveManager.savefile.readString())
		} else {
			player.position.set(1f, 1f)
		}

	}

	fun render(dt: Float, batch: SpriteBatch) {
		//Draw the world and everything
		GameMap.Env.render(dt, batch, player.position)
		GameMap.Overlay.render(dt, batch, player.position)
		GameMap.Entities.render(dt, batch, player.position)
		player.render(dt, batch)

	}

	fun renderUI(dt: Float, batch: SpriteBatch) {
		//Draw the UI
		Hotbar.render(batch)
		Healthbar.render(player.health.toInt(), batch)
		PlayerInventory.render(batch)

	}

	fun tick(dt: Float) {
		player.tick(dt)
		GameMap.Entities.tick(dt)
		//BADBADBAD
		if (GameMap.Entities.totalEntities() < 1) {
			val zombie1 = ZombieNoHand().apply { setPosition(8.0f, 9.0f) }
			val zombie2 = ZombieNoHandWithBlood().apply { setPosition(8.0f, 10.0f) }
			val zombie3 = ZombieWithHandWithBlood().apply { setPosition(8.0f, 11.0f) }
			val zombie4 = ZombieRunner().apply { setPosition(8.0f, 12.0f) }
			GameMap.Entities.spawn(zombie1, zombie2, zombie3, zombie4)

		}
		//ok here
		physics.step(dt, 8, 3) //TODO: the amount of time to simulate - dt or 1/20s?

		DaylightCycle.tick(dt)

	}

	private fun createLightHandler(): RayHandler {
		RayHandler.setGammaCorrection(true)
		RayHandler.useDiffuseLight(true)

		val rayhandler = RayHandler(physics)

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