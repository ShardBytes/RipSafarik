package com.shardbytes.ripsafarik.actors

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.shardbytes.ripsafarik.dataType

class CollisionListener : ContactListener {

    //TODO: why does this not pass the "P" and "Z"
    //???
    override fun beginContact(contact: Contact?) {
        println("beginContact")
        if(contact == null) return
        println("null check pass")
        contact.dataType<Player> { player, _ ->
            println("p")
            contact.dataType<Zombie> { zombie, _ ->
                println("z")
                val rotationVector = Vector2(MathUtils.cosDeg(zombie.rotation), MathUtils.sinDeg(zombie.rotation))

                player.body.applyLinearImpulse(rotationVector.setLength(zombie.knockbackForce), player.body.position, true)


            }

        }

    }
    override fun endContact(contact: Contact?) {}
    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {}
    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}

}