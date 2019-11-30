package com.shardbytes.ripsafarik.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.shardbytes.ripsafarik.Settings
import com.shardbytes.ripsafarik.actors.Camera
import com.shardbytes.ripsafarik.actors.GameWorld

class GameScreen : Screen {

    // rendering
    var camera = Camera(Camera.ResizeStrategy.CHANGE_ZOOM, Settings.GAME_V_WIDTH, Settings.GAME_V_HEIGHT)
    var batch = SpriteBatch()
    
    // world
    val world = GameWorld()
    val debugRenderer = Box2DDebugRenderer()
    
    
    init {
        camera.lockOn(world.player)
    }
    
    
    /*
    ==== update oredr ====
    It's easiest to think of your order in a single frame, think of it as a series of dependencies.

    User input depends on nothing, so it goes first.
    Objects being updated depend on the user input, so they go second.
    Physics depend on the new updated objects, so it goes third.
    Rendering depends on the latest physics state and object updates, so it goes fourth.
    UI depends on the scene to already be rendered, so it goes fifth.
     */
    override fun render(dt: Float) {
        // first act/tick
        world.tick(dt)
        
        // update camera before rendering
        camera.update()
        batch.projectionMatrix.set(camera.innerCamera.combined)
        
        // clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        
        // render stuff
        batch.begin()
        world.render(dt, batch)
        batch.end()
    
        // debug render world physics into camera matrix
        if (Settings.PHYSICS_DEBUG_ACTIVE) debugRenderer.render(world.physics, camera.innerCamera.combined)

        // render hud at the top
        batch.projectionMatrix.set(camera.innerCamera.projection)
        batch.begin()
        world.renderUI(dt, batch)
        batch.end()
        
    }

    override fun show() {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {
        camera.windowResized(width, height)
        
    }

    override fun dispose() {
        debugRenderer.dispose()

    }

    override fun hide() {

    }

}