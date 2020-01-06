package com.shardbytes.ripsafarik.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.components.world.Entity
import com.shardbytes.ripsafarik.components.world.Item
import com.shardbytes.ripsafarik.game.GameMap
import com.shardbytes.ripsafarik.game.GameWorld
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ktx.box2d.body

@Serializable
class ItemDrop(@Polymorphic private val item: Item) : Entity {

	override var maxHealth = 1f
	override var health = 1f
	override var regenSpeed = 0f

	@Transient
	private val font = BitmapFont().apply { data.setScale(0.1f) } //TODO: font scale fucks shit up, fix asap

	@Transient
	private var availableForPickup = false

	@Transient
	override val body: Body = GameWorld.physics.body(BodyDef.BodyType.StaticBody)

	override fun render(dt: Float, batch: SpriteBatch) {
		val originX = 0.5f
		val originY = 0.5f
		val originBasedPositionX = position.x - originX //TODO: redo naming because originX and originY mean something else
		val originBasedPositionY = position.y - originY

		//Draw the item
		batch.draw(TextureRegion(item.texture), originBasedPositionX, originBasedPositionY, originX, originY, 1.0f, 1.0f, 1f, 1f, body.angle * MathUtils.radDeg - 90f)

		//Pickup notice
		if (availableForPickup) {
			font.draw(batch, "Press F to pick up", position.x, position.y)

		}

	}

	override fun tick() {
		//If close enough to the player, set available for picking up
		availableForPickup = position.dst(GameWorld.player.position) < 1

		//And actually try to pick up, if key is pressed
		if (availableForPickup) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
				val successful = GameWorld.player.pickUp(item)
				if (successful) {
					GameMap.Entities.despawn(this)

				}

			}

		}

	}

	override fun dispose() {
		//y tho
	}

}