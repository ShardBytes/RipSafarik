package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.shardbytes.ripsafarik.tools.GameObject
import ktx.box2d.body
import ktx.box2d.createWorld

class GameWorld : GameObject {
    
    override val position = Vector2()
    
    val physics = createWorld() // world for physics
    val levelMain = LevelMain()
    
    init {
        // generate border
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
    }
    
    override fun act(dt: Float) {
        physics.step(dt, 6, 2)
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
    }
    
    override fun dispose() {
        physics.dispose()
    }

}
