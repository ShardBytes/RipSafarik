package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.shardbytes.ripsafarik.Settings
import com.shardbytes.ripsafarik.components.GameObject
import com.shardbytes.ripsafarik.map
import kotlin.math.min

/**
 * Simple ortographic camera that can be locked onto an object and will follow it.
 */
class Camera(resizeStrategy: ResizeStrategy,
             viewportWidth: Float = 0f,
             viewportHeight: Float = 0f,
             private var cameraPosition: Vector2 = Vector2(),
             private var lockTarget: GameObject? = null) {

    private var previousZoom = 1f
    private var newZoom = 1f

    private val zoomTime = 1f
    private var zoomTimeElapsed = 0f

    fun getZoom(): Float = newZoom

    fun setZoom(value: Float) {
        previousZoom = innerCamera.zoom
        newZoom = value

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
     * Enum defining what should camera do on window resize.
     */
    enum class ResizeStrategy {
        KEEP_ZOOM,
        CHANGE_ZOOM
    }
    
    /**
     * Use with caution.
     * @return Wrapped orthographic camera object for further customization.
     */
    var innerCamera: OrthographicCamera
        private set
    
    private var viewport: Viewport? = null
    
   // /**
   //  * Constructs a camera on [0, 0] world position.
   //  * @param strategy What should camera do when window is resized
   //  * @param width Width of camera's viewport
   //  * @param height Height of camera's viewport
   //  */
    init {
        if (resizeStrategy == ResizeStrategy.KEEP_ZOOM) {
            innerCamera = OrthographicCamera(viewportWidth, viewportHeight)
            viewport = FitViewport(viewportWidth, viewportHeight, innerCamera)
        } else {
            innerCamera = OrthographicCamera()
            viewport = FillViewport(viewportWidth, viewportHeight, innerCamera)
        }
        innerCamera.update()
    }
    
    
  //  /**
  //   * Unlocks camera from any locked object and sets its position manually.
  //   * @param pos Camera position
  //   */
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
        Settings.CURRENT_ASPECT_RATIO = width.toFloat() / height.toFloat()
        update()
        
    }

    /**
     * Recalculates what is camera rendering. Should be called each render tick outside
     * the begin()/end() block or after changing any camera attributes.
     * @see OrthographicCamera.update
     */
    fun update() {
        updateZoom()

        if (lockTarget == null) {
            innerCamera.position.set(cameraPosition, 0.0f)
        } else {
            innerCamera.position.set(lockTarget!!.position, 0.0f)
        }
        innerCamera.update()
        
    }

}