package com.shardbytes.ripsafarik.items

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.IUsable
import com.shardbytes.ripsafarik.components.world.Item
import com.shardbytes.ripsafarik.copyAndround
import com.shardbytes.ripsafarik.entity.Explosion
import com.shardbytes.ripsafarik.entity.Player
import com.shardbytes.ripsafarik.game.GameMap_new
import com.shardbytes.ripsafarik.screens.GameScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class DestroyTool : Item, IUsable {

	override val name: String = "destroyTool"
	override val displayName: String = "Destroy tool"
	@Transient
	override val texture: TextureRegion = TextureRegion(Textures.Item["tool/destroytool"])

	override val maxUses: Int = 0
	override var leftUses: Int = 0
	override var cooldown: Float = 0.1f

	override fun use(player: Player) {
		val screenX = Gdx.input.x
		val screenY = Gdx.input.y
		
		val screenCoords = GameScreen.camera.unproject(screenX, screenY)
		
		val mapCoords = Vector2(screenCoords.x, screenCoords.y).copyAndround()
		
		GameMap_new.removeTile(mapCoords)
		GameMap_new.spawn(Explosion(1.5f).apply { setPosition(mapCoords) })
		
	}

	override fun `break`(player: Player) {
		//does not break
		
	}


}