package com.shardbytes.ripsafarik.blocks

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.world.Block
import com.shardbytes.ripsafarik.game.GameWorld
import ktx.box2d.body

class Wall : Block {

	override val name: String = "wall"
	override val displayName: String = "Mineral wall"
	override val texture: TextureRegion = TextureRegion(Textures.Overlay["wall_R"])
	val texture2: TextureRegion = TextureRegion(Textures.Overlay["wall_U"])
	val texture3: TextureRegion = TextureRegion(Textures.Overlay["wall_LT"])

	override fun render(batch: SpriteBatch, x: Float, y: Float, sizeX: Float, sizeY: Float, rotation: Float) {
		when (rotation) {
			45f -> batch.draw(texture3, x - 0.5f, y - 0.5f, 0.5f, 0.5f, 1f, 1f, 1f, 1f, 0f)
			90f -> batch.draw(texture2, x - 0.5f, y - 0.5f, 0.5f, 0.5f, 1f, 1f, 1f, 1f, 0f)
			else -> batch.draw(texture, x - 0.5f, y - 0.5f, 0.5f, 0.5f, 1f, 1f, 1f, 1f, 0f)

		}

	}

	var bodies = mutableMapOf<String, Body>()

	override fun createCollider(coords: Vector2) {
		super.createCollider(coords)
		bodies.put(coords.toString(), GameWorld.physics.body(BodyDef.BodyType.StaticBody) {
			box(1f, 1f, coords)

		})

	}

	override fun onDestroy(coords: Vector2) {
		super.onDestroy(coords)
		GameWorld.physics.destroyBody(bodies[coords.toString()])
		bodies.remove(coords.toString())

	}

}