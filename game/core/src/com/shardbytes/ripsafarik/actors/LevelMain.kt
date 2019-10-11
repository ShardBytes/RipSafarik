package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.graphics.g2d.Sprite
import com.shardbytes.ripsafarik.assets.Textures

class LevelMain {
	
	val spriteMap = mapOf(
			"0a" to Sprite(Textures.Env["grass"]),
			"1a" to Sprite(Textures.Env["asfalt"]),
			"2a" to Sprite(Textures.Env["asfalt_ciara"]),
			"3a" to Sprite(Textures.Env["obrubnik"]),
			"3b" to Sprite(Textures.Env["obrubnik"]),
			"3c" to Sprite(Textures.Env["obrubnik"]),
			"3d" to Sprite(Textures.Env["obrubnik"]),
			"4a" to Sprite(Textures.Env["beton"]),
			"5a" to Sprite(Textures.Env["stairs"]),
			"5b" to Sprite(Textures.Env["stairs"]),
			"5c" to Sprite(Textures.Env["stairs"]),
			"5d" to Sprite(Textures.Env["stairs"]),
			"6c" to Sprite(Textures.Env["wall"]),
			"7c" to Sprite(Textures.Env["roof"]),
			"8a" to Sprite(Textures.Env["floor"]),
			"9a" to Sprite(Textures.Env["safarik"])
	
	).onEach {
		it.value.apply {
			setSize(1f, 1f)
			setOriginCenter()
		}
		
		when(it.key.last()) {
			'b' -> it.value.apply { rotation = 90f }
			'c' -> it.value.apply { rotation = 180f }
			'd' -> it.value.apply { rotation = 270f }
		}
	}
	
	val tileMap = arrayOf(
			arrayOf("0a", "0a", "1a", "1a", "1a", "1a", "1a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a"),
			
			arrayOf("0a", "0a", "1a", "1a", "1a", "1a", "1a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a"),
			
			arrayOf("0a", "0a", "1a", "1a", "1a", "1a", "1a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a"),
			
			arrayOf("0a", "0a", "1a", "1a", "1a", "1a", "1a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a"),
			
			arrayOf("0a", "0a", "1a", "1a", "1a", "1a", "1a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a"),
			
			arrayOf("0a", "0a", "1a", "1a", "1a", "1a", "1a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a", "0a"),
			
			arrayOf("3a", "3a", "1a", "1a", "1a", "1a", "1a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a", "3a"),
			
			arrayOf("1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a"),
			
			arrayOf("2a", "2a", "1a", "2a", "2a", "1a", "2a", "2a", "1a", "2a", "2a", "1a", "2a", "2a", "1a", "2a", "2a", "1a", "2a", "2a", "1a"),
			
			arrayOf("1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a", "1a"),
			
			arrayOf("3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c", "3c"),
			
			arrayOf("4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a", "4a"),
			
			arrayOf("6c", "6c", "6c", "6c", "6c", "6c", "4a", "4a", "5b", "5b", "5b", "8a", "8a", "8a", "8a", "5d", "5d", "5d", "4a", "4a", "6c"),
			
			arrayOf("7c", "7c", "7c", "7c", "7c", "7c", "4a", "4a", "5b", "5b", "5b", "8a", "8a", "8a", "8a", "5d", "5d", "5d", "4a", "4a", "7c")
	).reversedArray()
	
	val overlayMap = arrayOf(
			spriteMap.getValue("9a").apply { setSize(1f, 1f); setOriginCenter(); setPosition(13f, 0f) },
			spriteMap.getValue("9a").apply { setSize(1f, 1f); setOriginCenter(); setPosition(3f, 3f) }
	)
}