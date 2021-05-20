package com.shardbytes.ripsafarik.tools

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.LongMap
import com.shardbytes.ripsafarik.blocks.*
import com.shardbytes.ripsafarik.components.world.Block
import com.shardbytes.ripsafarik.components.world.Entity
import com.shardbytes.ripsafarik.components.world.Item
import com.shardbytes.ripsafarik.entity.ItemDrop
import com.shardbytes.ripsafarik.entity.zombie.ZombieNoHand
import com.shardbytes.ripsafarik.entity.zombie.ZombieNoHandWithBlood
import com.shardbytes.ripsafarik.entity.zombie.ZombieRunner
import com.shardbytes.ripsafarik.entity.zombie.ZombieWithHandWithBlood
import com.shardbytes.ripsafarik.game.GameMap
import com.shardbytes.ripsafarik.identifier
import com.shardbytes.ripsafarik.items.DestroyTool
import com.shardbytes.ripsafarik.items.Flashlight
import com.shardbytes.ripsafarik.items.Gun
import com.shardbytes.ripsafarik.items.GunMagazine
import com.shardbytes.ripsafarik.ui.inventory.Hotbar
import com.shardbytes.ripsafarik.ui.inventory.ItemSlot
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.internal.LongSerializer
import kotlinx.serialization.internal.StringSerializer
import kotlinx.serialization.internal.nullable
import kotlinx.serialization.json.*
import kotlinx.serialization.list
import kotlinx.serialization.modules.SerializersModule

object SaveManager {

	// Save path
	private val savefile = Gdx.files.local("world.map")
	private val polymorphicModule = SerializersModule {
		polymorphic(Item::class) {
			//class - its serializer
			//serializer is auto-generated when //@Serializable annotation is found
			//Gun::class with Gun.serializer()
			//Flashlight::class with Flashlight.serializer()
			//GunMagazine::class with GunMagazine.serializer()
			//DestroyTool::class with DestroyTool.serializer()

		}

		polymorphic(Entity::class) {
			//ZombieNoHand::class with ZombieNoHand.serializer()
			//ZombieNoHandWithBlood::class with ZombieNoHandWithBlood.serializer()
			//ZombieRunner::class with ZombieRunner.serializer()
			//ZombieWithHandWithBlood::class with ZombieWithHandWithBlood.serializer()

			//ItemDrop::class with ItemDrop.serializer()

		}

		polymorphic(Block::class) {
			//Grass::class with Grass.serializer()
			//Asfalt::class with Asfalt.serializer()
			//Concrete::class with Concrete.serializer()
			//Safarik::class with Safarik.serializer()
			//IronFence::class with IronFence.serializer()

		}

	}
	// JSON processor
	val json = Json(JsonConfiguration.Stable, context = polymorphicModule)

	fun save() {
		val chunks = serializeMap()
		//val hotbarItems = serializeHotbarItems()

		val save = JsonObject(mapOf("chunks" to chunks))
		val string = save.toString()
		savefile.writeString(string, false, "UTF-8")

	}

	private fun serializeMap(): JsonArray {
		val chunks = arrayListOf<JsonElement>()

		GameMap.chunks.forEach {
			// Chunk info
			val chunkLocation = it.value.chunkLocation.identifier()

			// Serialize tiles
			//val tiles = serializePolymorphicTilesMap(it.value.groundTiles)

			// Serialize entities
			val entities = serializePolymorphicList(it.value.entities)
			val entitiesToSpawn = serializePolymorphicList(it.value.entitiesToSpawn)
			val entitiesToRemove = serializePolymorphicList(it.value.entitiesToRemove)

			// Package it all together
			val jsonObj = JsonObject(mapOf(
					"chunkLocation" to JsonPrimitive(chunkLocation),
					//"tiles" to JsonArray(tiles),
					"entities" to JsonArray(entities),
					"entitiesToSpawn" to JsonArray(entitiesToSpawn),
					"entitiesToRemove" to JsonArray(entitiesToRemove)
			))

			chunks.add(jsonObj)

		}
		return JsonArray(chunks)

	}


	private fun serializeHotbarItems() {
		val hotbarItems = arrayListOf<JsonElement>()
/*
		//loop over each itemslot
		Hotbar.hotbarSlots.forEach {
			//and use different serializers to serialize it
			//eg. polymorphic (because Item can be any class specified above)
			val item = json.toJson(PolymorphicSerializer(Item::class), it.item ?: GunMagazine())

			//or other specified serializers (IntSerializer, FloatSerializer, ...)
			val position = json.toJson(Vector2Serializer, it.screenPosition)

			//put it all together into one object
			//add json tags so it's named somehow
			val jsonObj = JsonObject(mapOf("position" to position, "item" to item))

			//and add that slot into a map as an JSON object
			hotbarItems.add(jsonObj)

		}
		*/
		//val jsonnnn = json.toJson(ItemSlot.serializer().nullable.list, Hotbar.hotbarSlots.toList())
		//hotbarItems.add(jsonnnn)

		//make an array of all the slots (which are now JSON objects)
		//and put it to string
		val string = hotbarItems.toString()

		//save JSON string to file
		savefile.writeString(string, false, "UTF-8")

	}

	private inline fun <reified T : Any> serializePolymorphicList(list: List<T>): ArrayList<JsonElement> {
		val jsonList = arrayListOf<JsonElement>()
		list.forEach {
			val listElement = json.toJson(PolymorphicSerializer(T::class), it)
			jsonList.add(listElement)

		}
		return jsonList

	}

	private fun serializePolymorphicTilesMap(map: LongMap<Block>): ArrayList<JsonElement> {
		val jsonList = arrayListOf<JsonElement>()
		map.forEach {
			val listKey = json.toJson(LongSerializer, it.key)
			val listValue = json.toJson(StringSerializer, it.value.name)
			jsonList.add(JsonObject(mapOf("key" to listKey, "value" to listValue)))

		}
		return jsonList

	}

}