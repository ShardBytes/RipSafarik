package com.shardbytes.ripsafarik.tools

import com.badlogic.gdx.Gdx
import com.shardbytes.ripsafarik.game.GameWorld


object SaveManager {

	val savefile = Gdx.files.local("test.sav")

	fun save() {
		if (Gdx.files.isLocalStorageAvailable){
			savefile.writeString(GameWorld.player.position.toString(), false)
		} else {
			println("saving error")
		}

	}

}