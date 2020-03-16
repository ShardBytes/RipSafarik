package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.shardbytes.ripsafarik.dataType
import com.shardbytes.ripsafarik.entity.Bullet
import com.shardbytes.ripsafarik.entity.Player
import com.shardbytes.ripsafarik.entity.zombie.GenericZombie

class CollisionListener : ContactListener {

    override fun beginContact(contact: Contact?) {
        if(contact == null) return
        contact.dataType<Player> { player, _ ->
            contact.dataType<GenericZombie> { zombie, _ ->
                val knockbackVector = Vector2(MathUtils.cosDeg(zombie.rotation), MathUtils.sinDeg(zombie.rotation)).setLength(zombie.knockbackForce)

                //player.body.applyForceToCenter(knockbackVector, true)
                //zombie.body.applyForceToCenter(knockbackVector.rotate(180.0f).setLength(zombie.knockbackForce * 0.33f), true)

                //player.body.applyLinearImpulse(knockbackVector, player.body.position, true)
                //zombie.body.applyLinearImpulse(knockbackVector.rotate(180f).setLength(zombie.knockbackForce * 0.5f), zombie.body.position, true) //TODO: bad bad bad

                //player.takeDamage(20f)

            }

        }

        contact.dataType<Bullet> { bullet, _ ->
            contact.dataType<GenericZombie> { zombie, _ ->
                //bullet.despawn()
                //zombie.takeDamage(5f)

            }

        }

        contact.dataType<Bullet> { bullet, _ ->
            contact.dataType<Player> { player, _ ->
                //bullet.despawn()

            }
        }

    }
    override fun endContact(contact: Contact?) {}
    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {}
    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}

}