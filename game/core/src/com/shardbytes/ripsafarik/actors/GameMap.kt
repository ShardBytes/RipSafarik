package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils.clamp
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.JsonReader
import com.shardbytes.ripsafarik.Settings
import com.shardbytes.ripsafarik.components.BlockCatalog
import kotlin.math.roundToInt

class GameMap {
	
	fun loadAll(mapName: String) {
		val jsonString = Gdx.files.internal("world/${mapName}_map.json").readString()
		
		Env.load(jsonString)
		Overlay.load(jsonString)
		
	}
	
	object Env {
		private var env: MutableList<MutableList<String>> = mutableListOf()

		fun load(jsonString: String) {
			val mapJson = JsonReader().parse(jsonString).get("env")

			env.clear()
			mapJson.forEach {
				val arr = it.asStringArray()
				env.add(arr.toMutableList())

			}

		}
		
		fun render(dt: Float, batch: SpriteBatch, playerPos: Vector2) {
			val yMin = clamp(playerPos.y.toInt() - Settings.RENDER_DISTANCE, 0, env.size - 1)
			val yMax = clamp(playerPos.y.toInt() + Settings.RENDER_DISTANCE, 0, env.size - 1)
			
			for (y in yMin..yMax) {
				val row = env[y]
				val xMin = clamp(playerPos.x.toInt() - Settings.RENDER_DISTANCE, 0, row.size - 1)
				val xMax = clamp(playerPos.x.toInt() + Settings.RENDER_DISTANCE, 0, row.size - 1)
				
				for (x in xMin..xMax) {
					val cell = row[x]
					batch.draw(TextureRegion(BlockCatalog[cell]?.texture), x - 0.5f, y - 0.5f, 0.5f, 0.5f, 1f, 1f, 1f, 1f, 0f)
					
				}
				
			}
			
		}
		
		operator fun get(x: Int, y: Int) = env[y][x]
		
	}
	
	object Overlay {
		
		private var overlay: MutableList<BlockData> = mutableListOf()
		
		data class BlockData(
				var name: String,
				var posX: Int,
				var posY: Int,
				var scale: Float,
				var rotation: Float)
		
		fun load(jsonString: String) {
			val mapJson = JsonReader().parse(jsonString).get("overlay")
			
			overlay.clear()
			mapJson.forEach { 
				overlay.add(BlockData(
						it["name"].asString(),
						it["posX"].asInt(),
						it["posY"].asInt(),
						it["scale"].asFloat(),
						it["rotation"].asFloat())
				)
				
			}
			
		}
		
		fun render(dt: Float, batch: SpriteBatch, playerPos: Vector2) {
			for (overlayBlock in overlay) {
				val texture = BlockCatalog[overlayBlock.name]?.texture
				if(overlayBlock.posX + Settings.RENDER_DISTANCE > playerPos.x && playerPos.x > overlayBlock.posX - Settings.RENDER_DISTANCE) {
					if(overlayBlock.posY + Settings.RENDER_DISTANCE > playerPos.y && playerPos.y > overlayBlock.posY - Settings.RENDER_DISTANCE) {
						batch.draw(TextureRegion(texture), overlayBlock.posX - 0.5f, overlayBlock.posY - 0.5f, 0.5f, 0.5f, 1f, 1f, overlayBlock.scale, overlayBlock.scale, overlayBlock.rotation)
						
					}
					
				}
				
			}
			
		}
		
	}
	
}