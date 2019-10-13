package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.shardbytes.ripsafarik.Settings
import com.shardbytes.ripsafarik.components.GameObject

/**
 * Simple ortographic camera that can be locked onto an object and will follow it.
 */
class Camera(private val resizeStrategy: ResizeStrategy,
             private val viewportWidth: Float = 0f,
             private val viewportHeight: Float = 0f,
             private var cameraPosition: Vector2 = Vector2(),
             private var lockTarget: GameObject? = null) {
    
    
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
    
    /**
     * Constructs a camera on [0, 0] world position.
     * @param strategy What should camera do when window is resized
     * @param width Width of camera's viewport
     * @param height Height of camera's viewport
     */
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
    
    
    /**
     * Unlocks camera from any locked object and sets its position manually.
     * @param pos Camera position
     */
    var position: Vector2
        get() = cameraPosition
        set(pos) {
            lockTarget = null
            cameraPosition = pos
        }
    

    /**
     * Sets cameras lockObject to any GameObject.
     * @param object Object to lock the camera on
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
        println(viewport!!.screenHeight)
        update()
        
    }

    /**
     * Recalculates what is camera rendering. Should be called each render tick outside
     * the begin()/end() block or after changing any camera attributes.
     * @see OrthographicCamera.update
     */
    fun update() {
        if (lockTarget == null) {
            innerCamera.position.set(cameraPosition, 0.0f)
        } else {
            innerCamera.position.set(lockTarget!!.position, 0.0f)
        }
        innerCamera.update()
        
    }

}