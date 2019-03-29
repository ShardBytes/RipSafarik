package com.shardbytes.ripsafarik

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class GameScreen : Screen {

    var camera = Camera(Camera.ResizeStrategy.CHANGE_ZOOM, 10.0f, 10.0f)
    var batch = SpriteBatch()
    
    var gameObjects = mutableListOf<ITickable>()
    
    init {
        gameObjects.add(World())
        gameObjects.add(Player())
        
    }
    
    override fun render(delta: Float) {
        camera.update()
        batch.projectionMatrix = camera.innerCamera.combined
        
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        
        batch.begin()
        gameObjects.forEach { it.tick(batch, delta) }
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

    }

    override fun hide() {

    }

}
