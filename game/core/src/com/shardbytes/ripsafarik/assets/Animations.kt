package com.shardbytes.ripsafarik.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.shardbytes.ripsafarik.tools.GifDecoder

object Animations {

	operator fun get(animation: String) = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("textures/animated/$animation.gif").read())

}