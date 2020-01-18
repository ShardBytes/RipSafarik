package com.shardbytes.ripsafarik.tools

import com.badlogic.gdx.Gdx
import com.shardbytes.ripsafarik.components.world.Entity
import com.shardbytes.ripsafarik.components.world.Item
import com.shardbytes.ripsafarik.entity.ItemDrop
import com.shardbytes.ripsafarik.entity.zombie.ZombieNoHand
import com.shardbytes.ripsafarik.entity.zombie.ZombieNoHandWithBlood
import com.shardbytes.ripsafarik.entity.zombie.ZombieRunner
import com.shardbytes.ripsafarik.entity.zombie.ZombieWithHandWithBlood
import com.shardbytes.ripsafarik.game.GameMap_new
import com.shardbytes.ripsafarik.identifier
import com.shardbytes.ripsafarik.items.Flashlight
import com.shardbytes.ripsafarik.items.Gun
import com.shardbytes.ripsafarik.items.GunMagazine
import com.shardbytes.ripsafarik.ui.inventory.Hotbar
import com.shardbytes.ripsafarik.ui.inventory.ItemSlot
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.internal.nullable
import kotlinx.serialization.json.*
import kotlinx.serialization.list
import kotlinx.serialization.modules.SerializersModule

object SaveManager {

	//save path
	private val savefile = Gdx.files.local("world.map")
	private val polymorphicModule = SerializersModule {
		polymorphic(Item::class) {
			//class - its serializer
			//serializer is auto-generated when @Serializable annotation is found
			Gun::class with Gun.serializer()
			Flashlight::class with Flashlight.serializer()
			GunMagazine::class with GunMagazine.serializer()

		}

		polymorphic(Entity::class) {
			ZombieNoHand::class with ZombieNoHand.serializer()
			ZombieNoHandWithBlood::class with ZombieNoHandWithBlood.serializer()
			ZombieRunner::class with ZombieRunner.serializer()
			ZombieWithHandWithBlood::class with ZombieWithHandWithBlood.serializer()

			ItemDrop::class with ItemDrop.serializer()

		}

	}
	//json processor
	private val json = Json(JsonConfiguration.Stable, context = polymorphicModule)

	fun save() {
		//serializeMap()
		serializeHotbarItems()

	}

	private fun serializeMap() {
		val chunks = arrayListOf<JsonElement>()

		GameMap_new.chunks.forEach {
			//chunk info
			val chunkLocation = it.value.chunkLocation.identifier()

			//serialize tiles

			//serialize entities
			val entities = serializePolymorphicList(it.value.entities)
			val entitiesToSpawn = serializePolymorphicList(it.value.entitiesToSpawn)
			val entitiesToRemove = serializePolymorphicList(it.value.entitiesToRemove)

			val jsonObj = JsonObject(mapOf(
					"entities" to JsonArray(entities),
					"entitiesToSpawn" to JsonArray(entitiesToSpawn),
					"entitiesToRemove" to JsonArray(entitiesToRemove)
			))

			chunks.add(jsonObj)

		}

		val string = JsonArray(chunks).toString()
		savefile.writeString(string, false, "UTF-8")

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
		val jsonnnn = json.toJson(ItemSlot.serializer().nullable.list, Hotbar.hotbarSlots.toList())
		hotbarItems.add(jsonnnn)

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


}