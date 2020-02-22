package com.shardbytes.ripsafarik.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.shardbytes.ripsafarik.components.world.Entity
import com.shardbytes.ripsafarik.game.GameWorld
import com.shardbytes.ripsafarik.items.ItemStack
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class ItemDrop(private val itemStack: ItemStack) : Entity() {

	override var maxHealth = 1f
	override var health = 1f
	override var regenSpeed = 0f

	@Transient
	private val font = BitmapFont().apply { data.setScale(0.1f) } //TODO: font scale fucks shit up, fix asap

	@Transient
	private var availableForPickup = false

	override fun render(dt: Float, batch: SpriteBatch) {
		val originX = 0.5f
		val originY = 0.5f
		val originBasedPositionX = position.x - originX //TODO: redo naming because originX and originY mean something else
		val originBasedPositionY = position.y - originY

		//Draw the item
		batch.draw(TextureRegion(itemStack.item.texture), originBasedPositionX, originBasedPositionY, originX, originY, 1.0f, 1.0f, 1f, 1f, body.angle * MathUtils.radDeg - 90f)

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
				val successful = GameWorld.player.pickUp(itemStack)
				if (successful) {
					despawn()

				}

			}

		}

	}

	override fun dispose() {
		//y tho
	}

}