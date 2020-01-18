package com.shardbytes.ripsafarik

import com.badlogic.gdx.math.Vector2
import kotlinx.serialization.*
import kotlinx.serialization.internal.SerialClassDescImpl
import kotlin.math.floor
import kotlin.math.round

fun Vector2.inRange(start: Vector2, end: Vector2): Boolean {
	if (this.x >= start.x && this.x <= end.x) {
		if (this.y >= start.y && this.y <= end.y) {
			return true

		}

	}
	return false

}

fun Vector2.copyAndround(): Vector2 {
	return Vector2(round(this.x), round(this.y))

}

fun Vector2.identifier(): Long {
	val INT_MASK = 0b0000000000000000000000000000000011111111111111111111111111111111L

	val xComponent = this.x.toRawBits().toLong() and INT_MASK
	val yComponent = this.y.toRawBits().toLong() and INT_MASK

	var id = 0b0000000000000000000000000000000000000000000000000000000000000000L
	id = id or xComponent shl 32
	id = id or yComponent

	return id

}

fun Long.toVector(): Vector2 {
	val xComponent = Float.fromBits((this ushr 32).toInt())
	val yComponent = Float.fromBits(toInt())

	return Vector2(xComponent, yComponent)

}

fun Long.vectorXComponent() = Float.fromBits((this ushr 32).toInt())
fun Long.vectorYComponent() = Float.fromBits(toInt())

fun Vector2.floor(): Vector2 {
	x = floor(x)
	y = floor(y)

	return this

}

fun blockPosToChunkPos(blockPos: Vector2): Vector2 {
	return blockPos.cpy().add(0.5f, 0.5f).scl(1f / 16f).floor()

}

@Serializer(forClass = Vector2::class)
object Vector2Serializer : KSerializer<Vector2> {

	override val descriptor: SerialDescriptor = object : SerialClassDescImpl("Vector2") {
		init {
			addElement("x")
			addElement("y")

		}

	}

	override fun serialize(encoder: Encoder, obj: Vector2) {
		val compositeOutput = encoder.beginStructure(descriptor)
		compositeOutput.encodeFloatElement(descriptor, 0, obj.x)
		compositeOutput.encodeFloatElement(descriptor, 1, obj.y)
		compositeOutput.endStructure(descriptor)

	}

	override fun deserialize(decoder: Decoder): Vector2 {
		val decoder2 = decoder.beginStructure(descriptor)
		var x: Float? = null
		var y: Float? = null

		loop@ while (true) {
			when (val i = decoder2.decodeElementIndex(descriptor)) {
				CompositeDecoder.READ_DONE -> break@loop
				0 -> x = decoder2.decodeFloatElement(descriptor, i)
				1 -> y = decoder2.decodeFloatElement(descriptor, i)
				else -> throw SerializationException("Unknown index $i")

			}

		}
		decoder2.endStructure(descriptor)

		return Vector2(
				x ?: throw MissingFieldException("x"),
				y ?: throw MissingFieldException("y")
		)

	}

}