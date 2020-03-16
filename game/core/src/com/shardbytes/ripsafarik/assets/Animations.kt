package com.shardbytes.ripsafarik.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.shardbytes.ripsafarik.tools.GifDecoder

object Animations {

    private lateinit var buffer: Map<String, Animation<TextureRegion>>

    fun loadAll() {
        buffer = mutableMapOf(
                "animatedMonster" to load("animatedMonster"),
                "animatedMonster2" to load("animatedMonster2"),
                "animatedMonster3" to load("animatedMonster3"),
                "animatedFastMonster" to load("animatedFastMonster"),
                "animatedPlayer" to load("animatedPlayer"),
                "animatedExplosion" to load("animatedExplosion")
        )

    }

    private fun load(animation: String): Animation<TextureRegion> {
        return GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("textures/animated/$animation.gif").read())

    }

    operator fun get(animation: String): Animation<TextureRegion> = buffer[animation]
            ?: throw IllegalStateException("\"$animation\" not loaded")

}