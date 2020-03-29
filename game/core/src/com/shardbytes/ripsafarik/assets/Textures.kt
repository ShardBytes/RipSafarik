package com.shardbytes.ripsafarik.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.BitmapFontCache
import com.badlogic.gdx.utils.JsonReader
import ktx.assets.getAsset
import ktx.assets.load

object Textures {

	lateinit var manager: AssetManager

	/**
	 * Loads all textures into Asset Manager
	 */
	fun loadAll() {
		Env.load()
		Overlay.load()
		UI.load()
		Item.load()
		Entity.load()

	}

	object Env {

		fun load() {
			val jsonString = Gdx.files.internal("textures/envTextures.json").readString()
			val textures = JsonReader().parse(jsonString).get("textures").asStringArray()

			for (texture in textures) {
				val path = "textures/env/$texture.png"
				manager.load<Texture>(path)

			}

		}

		operator fun get(texture: String) = manager.getAsset<Texture>("textures/env/$texture.png")

	}

	object Overlay {

		fun load() {
			val jsonString = Gdx.files.internal("textures/overlayTextures.json").readString()
			val textures = JsonReader().parse(jsonString).get("textures").asStringArray()

			for (texture in textures) {
				val path = "textures/overlay/$texture.png"
				manager.load<Texture>(path)

			}

		}

		operator fun get(texture: String) = manager.getAsset<Texture>("textures/overlay/$texture.png")

	}

	object UI {

		fun load() {
			val jsonString = Gdx.files.internal("textures/uiTextures.json").readString()
			val textures = JsonReader().parse(jsonString).get("textures").asStringArray()

			for (texture in textures) {
				val path = "textures/ui/$texture.png"
				manager.load<Texture>(path)

			}

		}

		operator fun get(texture: String) = manager.getAsset<Texture>("textures/ui/$texture.png")

	}

	object Item {

		fun load() {
			val json = JsonReader().parse(Gdx.files.internal("textures/itemTextures.json").readString())
			val categories = json.get("categories").asStringArray()

			for (category in categories) {
				val textures = json.get(category).asStringArray()
				for (texture in textures) {
					val path = "textures/item/$category/$texture.png"
					manager.load<Texture>(path)

				}

			}

		}

		/**
		 * @param texture Texture address in format category/texture
		 * @return The requested texture
		 */
		operator fun get(texture: String) = manager.getAsset<Texture>("textures/item/$texture.png")

	}

	object Entity {

		fun load() {
			val jsonString = Gdx.files.internal("textures/entityTextures.json").readString()
			val textures = JsonReader().parse(jsonString).get("textures").asStringArray()

			for (texture in textures) {
				val path = "textures/entity/$texture.png"
				manager.load<Texture>(path)

			}

		}

		operator fun get(texture: String) = manager.getAsset<Texture>("textures/entity/$texture.png")

	}

	object Font {
		val bitmapFont = BitmapFontCache(BitmapFont().apply { data.setScale(1f) }).font

	}

}