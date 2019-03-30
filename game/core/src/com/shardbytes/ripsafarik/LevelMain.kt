package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.g2d.Sprite
import com.shardbytes.ripsafarik.assets.TexturesEnv

class LevelMain {
	
	val spriteMap = mapOf(
			"0a" to Sprite(TexturesEnv.grass()),
			"1a" to Sprite(TexturesEnv.asfalt()),
			"2a" to Sprite(TexturesEnv.asfalt_ciara()),
			"3a" to Sprite(TexturesEnv.obrubnik()),
			"3b" to Sprite(TexturesEnv.obrubnik()),
			"3c" to Sprite(TexturesEnv.obrubnik()),
			"3d" to Sprite(TexturesEnv.obrubnik()),
			"4a" to Sprite(TexturesEnv.beton()),
			"5a" to Sprite(TexturesEnv.stairs()),
			"5b" to Sprite(TexturesEnv.stairs()),
			"5c" to Sprite(TexturesEnv.stairs()),
			"5d" to Sprite(TexturesEnv.stairs()),
			"6c" to Sprite(TexturesEnv.wall()),
			"7c" to Sprite(TexturesEnv.roof()),
			"8a" to Sprite(TexturesEnv.floor()),
			"9a" to Sprite(TexturesEnv.runningorb())
	
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
			spriteMap.getValue("9a").apply { setSize(1f, 1f); setOriginCenter(); setPosition(13f, 8f) }
	)
}