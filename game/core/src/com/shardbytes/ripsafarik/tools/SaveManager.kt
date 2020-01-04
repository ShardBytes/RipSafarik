package com.shardbytes.ripsafarik.tools

import com.badlogic.gdx.Gdx
import com.shardbytes.ripsafarik.Vector2Serializer
import com.shardbytes.ripsafarik.components.world.Item
import com.shardbytes.ripsafarik.items.Flashlight
import com.shardbytes.ripsafarik.items.Gun
import com.shardbytes.ripsafarik.items.GunMagazine
import com.shardbytes.ripsafarik.ui.inventory.Hotbar
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.SerializersModule

object SaveManager {

	//save path
	val savefile = Gdx.files.local("test.sav")

	fun save() {
		//specifies which classes can be (de)serialized with polymorphic serializer
		val polymorphicModule = SerializersModule {
			polymorphic(Item::class) {
				//class - its serializer
				//serializer is auto-generated when @Serializable annotation is found
				Gun::class with Gun.serializer()
				Flashlight::class with Flashlight.serializer()
				GunMagazine::class with GunMagazine.serializer()

			}

		}

		//json processor
		val json = Json(JsonConfiguration.Stable, context = polymorphicModule)
		
		val hotbarItems = arrayListOf<JsonElement>()
		
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

		//make an array of all the slots (which are now JSON objects)
		//and put it to string
		val string = JsonArray(hotbarItems).toString()
		
		//save JSON string to file
		savefile.writeString(string, false, "UTF-8")

	}

}