package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.MathUtils.cosDeg
import com.badlogic.gdx.math.MathUtils.sinDeg



class Player : ILockable, ITickable{

    override val position: Vector2 = Vector2(0f, 0f)
    private val maxSpeed = 0.02
    private val rotationSpeed = 2.5
    private val interpolatedSpeed = Tween(0.0, 0.2, true, false)
    
    val sprite = Sprite(Texture("badlogic.jpg")).apply {
        setOriginCenter()
        setPosition(0f, 0f)
        setSize(1f, 1f)
        
    }

    override fun tick(batch: SpriteBatch, deltaTime: Float) {
        interpolatedSpeed.step(deltaTime)
        handleInput()
        sprite.draw(batch)
        
    }

    private fun handleInput() {
        /*
		 * Movement
		 */
        val rotation = sprite.getRotation()
        val xAmount = -interpolatedSpeed.float * MathUtils.sinDeg(rotation)
        val yAmount = interpolatedSpeed.float * MathUtils.cosDeg(rotation)

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            interpolatedSpeed.setTarget(maxSpeed)
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            interpolatedSpeed.setTarget(-maxSpeed / 2.0)
        } else {
            interpolatedSpeed.setTarget(0.0)
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            sprite.rotate(rotationSpeed.toFloat())
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            sprite.rotate((-rotationSpeed).toFloat())
        }

        position.add(xAmount, yAmount)
        sprite.translate(xAmount, yAmount)

        /*
		 * Shooting
		 */
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            //shoot()
        }

    }
    
}