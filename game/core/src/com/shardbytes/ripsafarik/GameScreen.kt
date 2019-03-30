package com.shardbytes.ripsafarik

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

class GameScreen : Screen {

    var camera = Camera(Camera.ResizeStrategy.CHANGE_ZOOM, 10.0f, 10.0f)
    var batch = SpriteBatch()
    
    var gameObjects = mutableListOf<ITickable>()
    val player = Player().apply { position.set(8f, 1f) }
    
    init {
        gameObjects.add(World())
        gameObjects.add(player)
        gameObjects.add(Zombie(player, Zombie.ZombieType.NO_HAND_BLOOD).apply { position.set(-2f, -2f) })
        gameObjects.add(Zombie(player, Zombie.ZombieType.HAND_BLOOD).apply { position.set(3f, -1f) })
        gameObjects.add(Zombie(player, Zombie.ZombieType.NO_HAND).apply { position.set(10f, 5f) })
        gameObjects.add(Zombie(player, Zombie.ZombieType.RUNNER).apply { position.set(7f, 9f) })
        
        camera.lockOn(player)
    }
    
    override fun render(delta: Float) {
        camera.update()
        batch.projectionMatrix.set(camera.innerCamera.combined)
        
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
