package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.shardbytes.ripsafarik.blocks.Asfalt
import com.shardbytes.ripsafarik.blocks.Grass
import com.shardbytes.ripsafarik.blocks.Safarik
import com.shardbytes.ripsafarik.components.BlockCatalog
import com.shardbytes.ripsafarik.ui.Hotbar
import ktx.box2d.createWorld

class GameWorld {
	
	val physics = createWorld()
	val player = Player(physics)
	
	init {
		//Register all blocks
		BlockCatalog.registerBlock(Grass())
		BlockCatalog.registerBlock(Asfalt())
		BlockCatalog.registerBlock(Safarik())
		
		player.position.set(1f, 1f)
		
	}
	
	fun render(dt: Float, batch: SpriteBatch) {
		//Draw the world and everything
		GameMap.Env.render(dt, batch, player.position)
		GameMap.Overlay.render(dt, batch, player.position)
		player.render(dt, batch)
		
	}
	
	fun renderUI(dt: Float, batch: SpriteBatch) {
		//Draw the UI
		Hotbar.render(dt, batch)
		
	}

	fun act(dt: Float) {
		player.act(dt)
		physics.step(dt, 10, 10) //TODO: the amount of time to simulate - dt or 1/20s?
		
	}

}