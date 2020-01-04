package com.shardbytes.ripsafarik.items

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.IUsable
import com.shardbytes.ripsafarik.components.world.Item
import com.shardbytes.ripsafarik.copyAndround
import com.shardbytes.ripsafarik.entity.Explosion
import com.shardbytes.ripsafarik.entity.Player
import com.shardbytes.ripsafarik.game.GameMap
import com.shardbytes.ripsafarik.screens.GameScreen

class DestroyTool : Item, IUsable {

	override val name: String = "destroyTool"
	override val displayName: String = "Destroy tool"
	override val texture: TextureRegion = TextureRegion(Textures.Item["tool/destroytool"])

	override val maxUses: Int = 0
	override var leftUses: Int = 0
	override var cooldown: Float = 0.5f

	override fun use(player: Player) {
		val screenX = Gdx.input.x
		val screenY = Gdx.input.y
		
		val screenCoords = GameScreen.camera.innerCamera.unproject(
				Vector3(screenX.toFloat(), screenY.toFloat(), 0f),
				GameScreen.camera.viewport!!.screenX.toFloat(),
				GameScreen.camera.viewport!!.screenY.toFloat(),
				GameScreen.camera.viewport!!.screenWidth.toFloat(),
				GameScreen.camera.viewport!!.screenHeight.toFloat())
		
		val mapCoords = Vector2(screenCoords.x, screenCoords.y)
		
		//TODO: remove this terrific explosion effect
		//remove overlay first, then try removing env
		val overlaySuccessful = GameMap.Overlay.remove(mapCoords)
		if(overlaySuccessful) {
			GameMap.Entities.spawn(Explosion(1.5f).apply { setPosition(mapCoords.copyAndround()) })

		}
		
		if(!overlaySuccessful) {
			val envSuccessful = GameMap.Env.remove(mapCoords)
			if(envSuccessful) {
				GameMap.Entities.spawn(Explosion(1.5f).apply { setPosition(mapCoords.copyAndround()) })

			}
			
		}
		
		
	}

	override fun `break`(player: Player) {
		//does not break
		
	}


}