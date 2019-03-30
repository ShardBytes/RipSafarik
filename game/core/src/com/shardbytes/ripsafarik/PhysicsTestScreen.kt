package com.shardbytes.ripsafarik

import com.badlogic.gdx.Gdx.gl
import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import ktx.box2d.body
import ktx.box2d.createWorld
import ktx.box2d.earthGravity
import kotlin.math.PI


class PhysicsTestScreen : Screen {
	
	private val cam = Camera(Camera.ResizeStrategy.CHANGE_ZOOM, 30f, 20f)
	private val batch = SpriteBatch()
	
	private val physWorld = createWorld(gravity = earthGravity)
	val debugRenderer = Box2DDebugRenderer()
	
	val bodies: MutableList<Body>
	val grund: Body
	val mousePos = Vector3()
	
	init {
		
		bodies = MutableList(100) { i -> physWorld.body(BodyDef.BodyType.DynamicBody) {
			circle(radius = 0.1f) {
				it.position.set(0f, 0f)
				friction = 0.6f
				restitution = 0.8f
				val mass = 0.1f // kg
				density = mass/(PI.toFloat()*0.1f*0.1f)
			}
			linearDamping = 0.5f
			position.set(i*0.001f, 0f)
		}}
		
		grund = physWorld.body(BodyDef.BodyType.StaticBody) {
			box(width = 20f, height = 0.5f) {
				friction = 1f
			}
			position.set(0f, -5f)
		}
	}
	
	override fun render(delta: Float) {
		
		mousePos.set(input.x*1f, input.y*1f, 0f)
		
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		cam.update()
		debugRenderer.render(physWorld, cam.innerCamera.combined)
		
		if (input.isTouched) {
			bodies.forEach { body ->
				val v = cam.innerCamera.unproject(mousePos.cpy())
				v.sub(Vector3(body.position, 0f))
				v.nor()
				v.setLength(10f)
				body.applyForceToCenter(v.x, v.y, true)
			}
		}
		
		physWorld.step(delta, 6, 2)
	}
	
	override fun resize(width: Int, height: Int) {
		cam.windowResized(width, height)
	}
	
	override fun dispose() {
		physWorld.dispose()
	}
	
	override fun hide() {}
	override fun show() {}
	override fun pause() {}
	override fun resume() {}

}