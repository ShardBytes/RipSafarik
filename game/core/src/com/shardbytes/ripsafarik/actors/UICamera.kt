package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.ScreenViewport

class UICamera {

    var innerCamera: OrthographicCamera
        private set
    var viewport: ScreenViewport

    init {
        innerCamera = OrthographicCamera()
        viewport = ScreenViewport(innerCamera)

        innerCamera.update()

    }

    fun windowResized(width: Int, height: Int) {
        viewport.update(width, height)
        innerCamera.update()

    }

    fun unproject(mouseX: Int, mouseY: Int): Vector2 {
        val unprojectedVector = innerCamera.unproject(
                Vector3(mouseX.toFloat(), mouseY.toFloat(), 0.0f),
                viewport.screenX.toFloat(),
                viewport.screenY.toFloat(),
                viewport.screenWidth.toFloat(),
                viewport.screenHeight.toFloat())

        return Vector2(unprojectedVector.x, unprojectedVector.y)

    }

    fun update() {
        innerCamera.update()

    }

}