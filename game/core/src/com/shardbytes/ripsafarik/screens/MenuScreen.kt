package com.shardbytes.ripsafarik.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.input.InputCore
import com.shardbytes.ripsafarik.game.MainGame

class MenuScreen : Screen {

	private val uiStage = Stage(
			ScalingViewport(
					Scaling.fit,
					Gdx.graphics.width.toFloat(),
					Gdx.graphics.height.toFloat()
			)

	)

	private val buttonHeight = uiStage.viewport.screenHeight / 9f
	private val buttonWidth = uiStage.viewport.screenWidth / 1.5f
	private val centeredButtonX = Gdx.graphics.width / 2 - buttonWidth / 2
	private val centeredButtonY = Gdx.graphics.height / 2 - buttonHeight / 2

	init {
		Gdx.input.inputProcessor = uiStage
		addUIElements()

	}

	private fun addUIElements() {
		val skin = Skin()
		val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
		pixmap.setColor(Color.WHITE)
		pixmap.fill()

		skin.add("white", Texture(pixmap))
		skin.add("defaultFont", BitmapFont())
		skin.add("whiteColour", Color.WHITE)

		val style = TextButtonStyle()
		style.font = skin.getFont("defaultFont")
		style.up = skin.newDrawable("white", Color.DARK_GRAY)
		style.down = skin.newDrawable("white", Color.DARK_GRAY)
		style.checked = skin.newDrawable("white", Color.BLUE)
		style.over = skin.newDrawable("white", Color.LIGHT_GRAY)
		skin.add("default", style)

		val style1 = LabelStyle()
		style1.font = skin.getFont("defaultFont")
		style1.fontColor = skin.getColor("whiteColour")
		skin.add("default", style1)
		val style2 = ScrollPaneStyle()

		style2.background = skin.newDrawable("white", Color.DARK_GRAY)
		style2.corner = skin.newDrawable("white", Color.CYAN)
		style2.vScrollKnob = skin.newDrawable("white", Color.WHITE)
		skin.add("default", style2)

		val playButton = TextButton("New game", skin)
		playButton.height = buttonHeight
		playButton.width = buttonWidth
		playButton.setPosition(centeredButtonX, centeredButtonY)

		val loadButton = TextButton("Load game", skin)
		loadButton.height = buttonHeight
		loadButton.width = buttonWidth
		loadButton.setPosition(centeredButtonX, centeredButtonY - buttonHeight*1.5f)

		val exitButton = TextButton("Exit", skin)
		exitButton.height = buttonHeight
		exitButton.width = buttonWidth
		exitButton.setPosition(centeredButtonX, centeredButtonY - buttonHeight*3f)

		playButton.addListener(object : ChangeListener() {
			override fun changed(event: ChangeEvent, actor: Actor) {
				Gdx.input.inputProcessor = InputCore.reset()
				MainGame.screen = GameScreen
				uiStage.dispose()

			}

		})

		exitButton.addListener(object : ChangeListener() {
			override fun changed(event: ChangeEvent, actor: Actor) {
				Gdx.app.exit()

			}

		})

		loadButton.addListener(object : ChangeListener() {
			override fun changed(event: ChangeEvent, actor: Actor) {
				Gdx.input.inputProcessor = InputCore.reset()
				MainGame.Data.loadSavedWorld = true
				MainGame.screen = GameScreen
				uiStage.dispose()

			}

		})

		val label = Label("RipSafarik", skin)
		label.setPosition(Gdx.graphics.width / 2 - label.width / 2, Gdx.graphics.height / 2 - label.height / 2 + 250)

		val safarik = Image(Textures.Overlay["safarik"])
		safarik.width = 100F
		safarik.height = 100F
		safarik.setPosition(Gdx.graphics.width / 2 - safarik.width / 2, Gdx.graphics.height / 2 - safarik.height / 2 + 150)

		uiStage.addActor(playButton)
		uiStage.addActor(loadButton)
		uiStage.addActor(exitButton)
		uiStage.addActor(safarik)
		uiStage.addActor(label)
	}

	override fun show() {}

	override fun render(delta: Float) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		uiStage.act(delta)
		uiStage.draw()

	}

	override fun resize(width: Int, height: Int) {
		uiStage.viewport.update(width, height)

	}

	override fun pause() {}
	override fun resume() {}
	override fun hide() {}

	override fun dispose() {
		uiStage.dispose()

	}

}