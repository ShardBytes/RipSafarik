package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.JsonReader
import com.shardbytes.ripsafarik.components.BlockCatalog

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
		
		fun render(dt: Float, batch: SpriteBatch) {
			for (y in 0..env.size - 1) {
				val row = env[y]
				for (x in 0..row.size - 1) {
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
		
		fun render(dt: Float, batch: SpriteBatch) {
			for (overlayBlock in overlay) {
				val texture = BlockCatalog[overlayBlock.name]?.texture
				batch.draw(TextureRegion(texture), overlayBlock.posX - 0.5f, overlayBlock.posY - 0.5f, 0.5f, 0.5f, 1f, 1f, overlayBlock.scale, overlayBlock.scale, overlayBlock.rotation)
				
			}
			
		}
		
	}
	
}