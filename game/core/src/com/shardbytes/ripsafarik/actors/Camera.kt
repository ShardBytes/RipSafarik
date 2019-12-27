package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.shardbytes.ripsafarik.Settings
import com.shardbytes.ripsafarik.components.GameObject
import com.shardbytes.ripsafarik.map
import kotlin.math.min

/**
 * Simple ortographic camera that can be locked onto an object and will follow it.
 */
class Camera(viewportWidth: Float = 0f,
             viewportHeight: Float = 0f,
             private var tweenZoom: Boolean = false,
             private var cameraPosition: Vector2 = Vector2(),
             private var lockTarget: GameObject? = null) {

    private var previousZoom = 1f
    private var newZoom = 1f

    private val zoomTime = 1f
    private var zoomTimeElapsed = 0f

    fun getZoom(): Float = if(tweenZoom) newZoom else innerCamera.zoom

    fun setZoom(value: Float) {
        if(tweenZoom) {
            previousZoom = innerCamera.zoom
            newZoom = value

            if(zoomTimeElapsed > 0) zoomTimeElapsed *= 0.5f
            
        } else {
            innerCamera.zoom = value
            
        }

    }

    private fun updateZoom() {
        if(newZoom != innerCamera.zoom) {
            val rangeMin = 0f
            val rangeMax = 1f

            val pointMin = previousZoom
            val pointMax = newZoom

            val progress = min(zoomTime, zoomTimeElapsed / zoomTime)
            val alpha = Interpolation.fade.apply(progress)

            innerCamera.zoom = map(alpha, rangeMin, rangeMax, pointMin, pointMax)

            zoomTimeElapsed += Gdx.graphics.deltaTime

        } else {
            zoomTimeElapsed = 0f

        }

    }

    /**
     * Use with caution.
     * @return Wrapped orthographic camera object for further customization.
     */
    var innerCamera: OrthographicCamera
        private set

    var viewport: Viewport? = null

    init {
        innerCamera = OrthographicCamera(viewportWidth, viewportHeight)
        viewport = FillViewport(viewportWidth, viewportHeight, innerCamera)
        innerCamera.update()
        
    }
    
    var position: Vector2
        get() = cameraPosition
        set(pos) {
            lockTarget = null
            cameraPosition = pos
        }


    /**
     * Sets cameras lockObject to any GameObject.
     * @param target Object to lock the camera on
     */
    fun lockOn(target: GameObject) {
        lockTarget = target
    }

    /**
     * Must be called whenever the window has resized to change camera's zoom
     * or viewport size accordingly.
     * @param width New window screenWidth
     * @param height New window screenHeight
     */
    fun windowResized(width: Int, height: Int) {
        viewport!!.update(width, height)
        update()

    }

    /**
     * Recalculates what is camera rendering. Should be called each render tick outside
     * the begin()/end() block or after changing any camera attributes.
     * @see OrthographicCamera.update
     */
    fun update() {
        if(tweenZoom) updateZoom()

        if (lockTarget == null) {
            innerCamera.position.set(cameraPosition, 0.0f)
        } else {
            innerCamera.position.set(lockTarget!!.position, 0.0f)
        }
        innerCamera.update()

    }
    
    fun unproject(mouseX: Int, mouseY: Int): Vector2 {
        val unprojectedVector = innerCamera.unproject(
                Vector3(mouseX.toFloat(), mouseY.toFloat(), 0.0f),
                viewport!!.screenX.toFloat(),
                viewport!!.screenY.toFloat(),
                viewport!!.screenWidth.toFloat(),
                viewport!!.screenHeight.toFloat())
        
        return Vector2(unprojectedVector.x, unprojectedVector.y)
        
    }

}