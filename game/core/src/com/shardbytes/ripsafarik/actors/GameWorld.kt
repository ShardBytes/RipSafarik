package com.shardbytes.ripsafarik.actors

import box2dLight.RayHandler
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import com.shardbytes.ripsafarik.blocks.Asfalt
import com.shardbytes.ripsafarik.blocks.Concrete
import com.shardbytes.ripsafarik.blocks.Grass
import com.shardbytes.ripsafarik.blocks.Safarik
import com.shardbytes.ripsafarik.components.BlockCatalog
import com.shardbytes.ripsafarik.components.DaylightCycle
import com.shardbytes.ripsafarik.entity.Player
import com.shardbytes.ripsafarik.entity.Zombie
import com.shardbytes.ripsafarik.ui.Healthbar
import com.shardbytes.ripsafarik.ui.inventory.Hotbar
import com.shardbytes.ripsafarik.ui.inventory.PlayerInventory
import ktx.box2d.createWorld

object GameWorld: Disposable {
	
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
		
		player.position.set(1f, 1f)
		
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
		Hotbar.render(dt, batch)
		Healthbar.render(player.health.toInt(), batch)
		PlayerInventory.render(dt, batch)
		
	}

	fun tick(dt: Float) {
		player.tick(dt)
		GameMap.Entities.tick(dt)
		//BADBADBAD
		if(GameMap.Entities.totalEntities() < 3) {
			val zombie = Zombie(this, Zombie.ZombieType.values().random()).apply { setPosition(8.0f, 9.0f) }
			GameMap.Entities.spawn(zombie)

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