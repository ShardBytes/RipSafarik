package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.actors.Player
import com.shardbytes.ripsafarik.actors.Zombie
import com.shardbytes.ripsafarik.components.GameObject
import ktx.box2d.body
import ktx.box2d.createWorld

class GameWorld : GameObject {
    
    override val position = Vector2()
    
    val physics = createWorld() // world for physics
    val levelMain = LevelMain()
    
    // entities
    val player = Player(this).apply { position.set(8f, 1f) }
    val zombies = mutableListOf<Zombie>()
    
    init {
        // generate bodies for static tiles
        for ((y, row) in levelMain.tileMap.withIndex()) {
            for ((x, tile) in row.withIndex()) {
                if (tile == "0a") { // if grass
                    physics.body(BodyDef.BodyType.StaticBody) {
                        box(width = 1f, height = 1f)
                        position.set(x*1f, y*1f)
                    }
                }
            }
        }
        
        // spawn some zombies
        zombies.add(Zombie(this, Zombie.ZombieType.NO_HAND_BLOOD).apply { setPosition(-2f, -2f) })
        zombies.add(Zombie(this, Zombie.ZombieType.HAND_BLOOD).apply { setPosition(3f, -1f) })
        zombies.add(Zombie(this, Zombie.ZombieType.NO_HAND).apply { setPosition(10f, 5f) })
        zombies.add(Zombie(this, Zombie.ZombieType.RUNNER).apply { setPosition(7f, 9f) })
    }
    
    override fun act(dt: Float) {
        player.act(dt)
        zombies.forEach { it.act(dt) }
        physics.step(dt, 6, 2) // update physics of world after user input!!
    }

    override fun render(dt: Float, batch: SpriteBatch) {
        
        // draw level
        for ((y, row) in levelMain.tileMap.withIndex()) {
            for ((x, value) in row.withIndex()) {
                levelMain.spriteMap.getValue(value).apply { setPosition(x*1f - 0.5f, y*1f - 0.5f) }.draw(batch)
            }
        }
        
        // draw overlay
        levelMain.overlayMap.forEach {
            it.draw(batch)
        }
        
        // zombies
        zombies.forEach { it.render(dt, batch) }
        
        // player
        player.render(dt, batch)
    }
    
    override fun dispose() {
        physics.dispose()
    }

}
