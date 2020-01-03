package com.shardbytes.ripsafarik.tools

import com.badlogic.gdx.Gdx
import com.shardbytes.ripsafarik.Vector2Serializer
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.world.Item
import com.shardbytes.ripsafarik.entity.ItemDrop
import com.shardbytes.ripsafarik.items.Flashlight
import com.shardbytes.ripsafarik.items.Gun
import com.shardbytes.ripsafarik.items.GunMagazine
import com.shardbytes.ripsafarik.ui.inventory.Hotbar
import com.shardbytes.ripsafarik.ui.inventory.ItemSlot
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.internal.ArrayListSerializer
import kotlinx.serialization.internal.IntArraySerializer
import kotlinx.serialization.internal.IntSerializer
import kotlinx.serialization.internal.ReferenceArraySerializer
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import kotlinx.serialization.modules.serializersModule
import kotlinx.serialization.modules.serializersModuleOf
import kotlinx.serialization.serializer
import kotlinx.serialization.stringify
import java.util.ArrayList

object SaveManager {

	val savefile = Gdx.files.local("test.sav")

	fun save() {
		val polymorphicModule = SerializersModule { 
			polymorphic(Item::class) { 
				Gun::class with Gun.serializer()
				Flashlight::class with Flashlight.serializer()
				GunMagazine::class with GunMagazine.serializer()
				
			}
			
		}
		
		val json = Json(JsonConfiguration.Stable, context = polymorphicModule)

		val hotbarItems = arrayListOf<JsonElement>()
		Hotbar.hotbarSlots.forEach {
			val item = json.toJson(PolymorphicSerializer(Item::class), it.item ?: GunMagazine())
			val position = json.toJson(Vector2Serializer, it.screenPosition)
			
			val jsonObj = JsonObject(mapOf("position" to position, "item" to item))
			
			hotbarItems.add(jsonObj)
			
		}
		
		val string = JsonArray(hotbarItems).toString()
		savefile.writeString(string, false, "UTF-8")
		
	}

}