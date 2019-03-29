package com.shardbytes.ripsafarik

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

class Player : ILockable, ITickable{

    override val position: Vector2 = Vector2(0f, 0f)
    
    val sprite = Sprite(Texture("badlogic.jpg")).apply {
        setOriginCenter()
        setPosition(0f, 0f)
        setSize(1f, 1f)
        
    }

    override fun tick(batch: SpriteBatch, deltaTime: Float) {
        sprite.draw(batch)
        
    }
    
    fun handleInput() {
        private void handleInput(){
            /*
             * Movement
             */
            float rotation = playerSprite.getRotation();
            float xAmount = -interpolatedSpeed.getFloat() * MathUtils.sinDeg(rotation);
            float yAmount = interpolatedSpeed.getFloat() * MathUtils.cosDeg(rotation);

            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                interpolatedSpeed.setTarget(maxSpeed);
            }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                interpolatedSpeed.setTarget(-maxSpeed / 2.0d);
            }else{
                interpolatedSpeed.setTarget(0.0d);
            }

            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                playerSprite.rotate(rotationSpeed);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                playerSprite.rotate(-rotationSpeed);
            }

            position.add(xAmount, yAmount);
            playerSprite.translate(xAmount, yAmount);

            /*
             * Shooting
             */
            if(Gdx.input.isKeyPressed(Input.Keys.Q)){
                shoot();
            }

        }
        
    }
    
}