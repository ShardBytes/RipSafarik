package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.shardbytes.ripsafarik.dataType

class CollisionListener : ContactListener {

    override fun beginContact(contact: Contact?) {
        if(contact == null) return
        contact.dataType<Player> { player, _ ->
            contact.dataType<Zombie> { zombie, _ ->
                val knockbackVector = Vector2(MathUtils.cosDeg(zombie.rotation), MathUtils.sinDeg(zombie.rotation)).setLength(zombie.knockbackForce)

                //player.body.applyForceToCenter(knockbackVector, true)
                //zombie.body.applyForceToCenter(knockbackVector.rotate(180.0f).setLength(zombie.knockbackForce * 0.33f), true)

                player.body.applyLinearImpulse(knockbackVector, player.body.position, true)
                zombie.body.applyLinearImpulse(knockbackVector.setLength(zombie.knockbackForce * 0.33f), zombie.body.position, true)


            }

        }

    }
    override fun endContact(contact: Contact?) {}
    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {}
    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}

}