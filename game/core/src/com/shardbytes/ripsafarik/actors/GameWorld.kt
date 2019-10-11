package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.blocks.Grass
import com.shardbytes.ripsafarik.components.GameObject
import ktx.box2d.FixtureDefinition
import ktx.box2d.body
import ktx.box2d.createWorld

class GameWorld : GameObject {
	
	override val position: Vector2 = Vector2()
	
	val physics = createWorld()
	val player = Player(physics)
	
	val grassBlock = Grass()
	
	init {
		
		
	}
	
	override fun render(dt: Float, batch: SpriteBatch) {
		grassBlock.render(dt, batch, Vector2(0f, 0f))
		
	}

	override fun act(dt: Float) {
		physics.step(dt, 10, 10) //TODO: the amount of time to simulate - dt or 1/20s?
		
	}

	override fun dispose() {
		
	}

}