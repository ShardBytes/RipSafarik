package com.shardbytes.ripsafarik.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.shardbytes.ripsafarik.game.MainGame
import com.shardbytes.ripsafarik.components.input.InputCore
import com.shardbytes.ripsafarik.entity.Player
import com.shardbytes.ripsafarik.tools.SaveManager
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.stringify

//TODO: Šimon plz finish
//no u

class PauseScreen : Screen {

	private val uiStage = Stage(
			ScalingViewport(
					Scaling.fit,
					Gdx.graphics.width.toFloat(),
					Gdx.graphics.height.toFloat()
			)

	)

	private var buttonHeight = uiStage.viewport.screenHeight / 9f
	private var buttonWidth = uiStage.viewport.screenWidth / 5f
	private var centeredButtonX = uiStage.viewport.screenWidth / 3 / 2 - buttonWidth / 2
	private var centeredButtonY = uiStage.viewport.screenHeight / 2 - buttonHeight / 2

	private var label: Label
	private var loadButton: TextButton
	private var saveButton: TextButton
	private var exitButton: TextButton

	init {
		Gdx.input.inputProcessor = uiStage
		val skin = createSkin()

		loadButton = TextButton("Resume", skin)
		loadButton.height = buttonHeight
		loadButton.width = buttonWidth
		loadButton.setPosition(centeredButtonX, centeredButtonY)
		loadButton.addListener(object : ChangeListener() {
			override fun changed(event: ChangeEvent, actor: Actor) {
				Gdx.input.inputProcessor = InputCore.reset()
				MainGame.screen = GameScreen
				uiStage.dispose()

			}

		})

		saveButton = TextButton("Save", skin)
		saveButton.height = buttonHeight
		saveButton.width = buttonWidth
		saveButton.setPosition(centeredButtonX, centeredButtonY - buttonHeight * 1.25f)
		saveButton.addListener(object : ChangeListener() {
			override fun changed(event: ChangeEvent, actor: Actor) {
				SaveManager.save()
				Gdx.input.inputProcessor = InputCore.reset()
				MainGame.screen = GameScreen
				uiStage.dispose()

			}

		})

		exitButton = TextButton("Exit", skin)
		exitButton.height = buttonHeight
		exitButton.width = buttonWidth
		exitButton.setPosition(centeredButtonX, centeredButtonY - buttonHeight * 2.5f)
		exitButton.addListener(object : ChangeListener() {
			override fun changed(event: ChangeEvent, actor: Actor) {
				Gdx.app.exit()

			}

		})

		label = Label("Paused", skin)
		label.setPosition(Gdx.graphics.width / 3 / 2 - label.width / 2, Gdx.graphics.height / 2 - label.height / 2 + Gdx.graphics.height / 5)

		uiStage.addActor(loadButton)
		uiStage.addActor(saveButton)
		uiStage.addActor(exitButton)
		uiStage.addActor(label)

	}

	private fun createSkin(): Skin {
		val skin = Skin()
		val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
		pixmap.setColor(Color.WHITE)
		pixmap.fill()

		skin.add("white", Texture(pixmap))
		skin.add("defaultFont", BitmapFont())
		skin.add("whiteColour", Color.WHITE)

		val style = TextButton.TextButtonStyle()
		style.font = skin.getFont("defaultFont")
		style.up = skin.newDrawable("white", Color.DARK_GRAY)
		style.down = skin.newDrawable("white", Color.DARK_GRAY)
		style.checked = skin.newDrawable("white", Color.BLUE)
		style.over = skin.newDrawable("white", Color.LIGHT_GRAY)
		skin.add("default", style)

		val style1 = Label.LabelStyle()
		style1.font = skin.getFont("defaultFont")
		style1.fontColor = skin.getColor("whiteColour")
		skin.add("default", style1)
		val style2 = ScrollPane.ScrollPaneStyle()

		style2.background = skin.newDrawable("white", Color.DARK_GRAY)
		style2.corner = skin.newDrawable("white", Color.CYAN)
		style2.vScrollKnob = skin.newDrawable("white", Color.WHITE)
		skin.add("default", style2)

		return skin

	}

	override fun show() {}

	override fun render(delta: Float) {
		//OpenGL magic to limit modifiable screen size to 1/3
		//requires OpenGL 3.0 tho
		Gdx.gl.glEnable(GL30.GL_SCISSOR_TEST)
		Gdx.gl.glScissor(0, 0, uiStage.viewport.screenWidth / 3, uiStage.viewport.screenHeight)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

		uiStage.act(delta)
		uiStage.draw()

		Gdx.gl.glDisable(GL30.GL_SCISSOR_TEST)

	}

	override fun resize(width: Int, height: Int) {
		uiStage.viewport.update(width, height)

		buttonHeight = height / 9f
		buttonWidth = width / 5f
		centeredButtonX = width / 3 / 2 - buttonWidth / 2
		centeredButtonY = height / 2 - buttonHeight / 2

		loadButton.setPosition(centeredButtonX, centeredButtonY)
		exitButton.setPosition(centeredButtonX, centeredButtonY - buttonHeight * 2.5f)

		label.setPosition(Gdx.graphics.width / 3 / 2 - label.width / 2, Gdx.graphics.height / 2 - label.height / 2 + Gdx.graphics.height / 5)

	}

	override fun pause() {}
	override fun resume() {}
	override fun hide() {}

	override fun dispose() {
		uiStage.dispose()

	}

}