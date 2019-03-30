package com.shardbytes.ripsafarik

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.shardbytes.ripsafarik.actors.Player
import com.shardbytes.ripsafarik.actors.Zombie
import com.shardbytes.ripsafarik.actors.Camera

class GameScreen : Screen {

    // rendering
    var camera = Camera(Camera.ResizeStrategy.CHANGE_ZOOM, Meta.V_WIDTH, Meta.V_HEIGHT)
    var batch = SpriteBatch()
    
    // game
    val world = GameWorld()
    val player = Player().apply { position.set(8f, 1f) }
    val zombies = mutableListOf<Zombie>()
    
    init {
        zombies.add(Zombie(player, Zombie.ZombieType.NO_HAND_BLOOD).apply { position.set(-2f, -2f) })
        zombies.add(Zombie(player, Zombie.ZombieType.HAND_BLOOD).apply { position.set(3f, -1f) })
        zombies.add(Zombie(player, Zombie.ZombieType.NO_HAND).apply { position.set(10f, 5f) })
        zombies.add(Zombie(player, Zombie.ZombieType.RUNNER).apply { position.set(7f, 9f) })
        
        camera.lockOn(player)
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
        // first act
        player.act(dt)
        zombies.forEach { it.act(dt) }
        world.act(dt) // update physics of world after user input!!
        
        // update camera before rendering
        camera.update()
        batch.projectionMatrix.set(camera.innerCamera.combined)
        
        // clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        
        // render
        batch.begin()
        world.render(dt, batch)
        zombies.forEach { it.render(dt, batch) }
        player.render(dt, batch)
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
