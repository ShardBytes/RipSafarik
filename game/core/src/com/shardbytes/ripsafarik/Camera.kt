package com.shardbytes.ripsafarik

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.*

/**
 * Simple ortographic camera that can be locked onto an object and will follow it.
 */
class Camera : ILockable {

    /**
     * Use with caution.
     * @return Wrapped orthographic camera object for further customization.
     */
    var innerCamera: OrthographicCamera? = null
        private set
    private var cameraPosition: Vector2
    private var resizeStrategy: ResizeStrategy
    private var lockObject: ILockable? = null
    private var viewport: Viewport? = null

    private val screenWidth = Gdx.graphics.width.toFloat()
    private val screenHeight = Gdx.graphics.height.toFloat()
    private var viewportWidth: Float = 0.toFloat()
    private var viewportHeight: Float = 0.toFloat()

    /**
     * Unlocks camera from any potential locked object and sets its position manually.
     * @param pos Camera position
     */
    override var position: Vector2
        get() = cameraPosition
        set(pos) {
            lockObject = null
            cameraPosition = pos

        }

    /**
     * Enum defining what should camera do on window resize.
     */
    enum class ResizeStrategy {
        KEEP_ZOOM,
        CHANGE_ZOOM

    }

    /**
     * Constructs a camera on [0, 0] world position.
     * @param strategy What should camera do when window is resized
     * @param width Width of camera's viewport
     * @param height Height of camera's viewport
     */
    constructor(strategy: ResizeStrategy, width: Float, height: Float) {
        resizeStrategy = strategy
        cameraPosition = Vector2()
        viewportWidth = width
        viewportHeight = height
        construct()

    }

    /**
     * Constructs a camera on a given world position.
     * @param strategy What should camera do when window is resized
     * @param position Camera's position
     * @param width Width of camera's viewport
     * @param height Height of camera's viewport
     */
    constructor(strategy: ResizeStrategy, position: Vector2, width: Float, height: Float) {
        resizeStrategy = strategy
        cameraPosition = position
        viewportWidth = width
        viewportHeight = height
        construct()

    }

    /**
     * Constructs a camera that follows the given object.
     * @param strategy What should camera do when window is resized
     * @param object Object to lock the camera on
     * @param width Width of camera's viewport
     * @param height Height of camera's viewport
     */
    constructor(strategy: ResizeStrategy, `object`: ILockable, width: Float, height: Float) {
        resizeStrategy = strategy
        cameraPosition = `object`.position
        lockObject = `object`
        viewportWidth = width
        viewportHeight = height
        construct()

    }

    private fun construct() {
        if (resizeStrategy == ResizeStrategy.CHANGE_ZOOM) {
            innerCamera = OrthographicCamera(viewportWidth, viewportHeight)
            viewport = FitViewport(viewportWidth, viewportHeight, innerCamera)
        } else {
            innerCamera = OrthographicCamera()
            viewport = ScreenViewport(innerCamera)
        }

        innerCamera!!.update()

    }

    /**
     * Sets camera's lockObject to any object implementing ILockable interface.
     * @param object Object to lock the camera on
     */
    fun lockOn(target: ILockable) {
        lockObject = target
    }

    /**
     * Method that must be called whenever the window has resized to change camera's zoom
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
        if (lockObject == null) {
            innerCamera!!.position.set(cameraPosition!!, 0.0f)
        } else {
            innerCamera!!.position.set(lockObject!!.position, 0.0f)
        }

        innerCamera!!.update()

    }

}