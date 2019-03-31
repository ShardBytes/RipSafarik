package com.shardbytes.ripsafarik

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.shardbytes.ripsafarik.actors.Player
import com.shardbytes.ripsafarik.actors.Zombie
import com.shardbytes.ripsafarik.actors.Camera

class GameScreen : Screen {

    // rendering
    var camera = Camera(Camera.ResizeStrategy.CHANGE_ZOOM, Meta.V_WIDTH, Meta.V_HEIGHT)
    var batch = SpriteBatch()
    
    // world
    val world = GameWorld()
    val debugRenderer = Box2DDebugRenderer()
    
    // entities
    val player = Player(world).apply { position.set(8f, 1f) }
    val zombies = mutableListOf<Zombie>()
    
    init {
        zombies.add(Zombie(world, player, Zombie.ZombieType.NO_HAND_BLOOD).apply { setPosition(-2f, -2f) })
        zombies.add(Zombie(world, player, Zombie.ZombieType.HAND_BLOOD).apply { setPosition(3f, -1f) })
        zombies.add(Zombie(world, player, Zombie.ZombieType.NO_HAND).apply { setPosition(10f, 5f) })
        zombies.add(Zombie(world, player, Zombie.ZombieType.RUNNER).apply { setPosition(7f, 9f) })
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
        
        // entities
        world.render(dt, batch)
        zombies.forEach { it.render(dt, batch) }
        player.render(dt, batch)
        
        batch.end()
    
        // debug
        if (Meta.PHYSICS_DEBUG_ACTIVE) debugRenderer.render(world.physics, camera.innerCamera.combined)
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
