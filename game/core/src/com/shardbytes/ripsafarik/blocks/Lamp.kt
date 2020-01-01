package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.world.Block
import com.shardbytes.ripsafarik.game.GameWorld
import ktx.box2d.body

class Lamp : Block {

	override val name = "lamp"
	override val displayName = "Street lamp"
	override val texture = TextureRegion(Textures.Overlay["lamp"])
	
	var bodies = mutableMapOf<Vector2, Body>()

	override fun createCollider(coords: Vector2) {
		super.createCollider(coords)
		bodies.put(coords, GameWorld.physics.body(BodyDef.BodyType.StaticBody) { 
			circle(0.5f, coords)
			
		})
		
	}

	override fun onDestroy(coords: Vector2) {
		super.onDestroy(coords)
		bodies.remove(coords)
		
	}

}