package com.shardbytes.ripsafarik.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.shardbytes.ripsafarik.MainGame
import com.shardbytes.ripsafarik.components.input.InputCore

//TODO: Šimon plz finish
class PauseScreen : Screen {

	private val uiStage = Stage(
			ScalingViewport(
					Scaling.fit,
					Gdx.graphics.width.toFloat(),
					Gdx.graphics.height.toFloat()
			)

	)

	private val buttonHeight = 50f
	private val buttonWidth = 500f / 3f
	private val centeredButtonX = (Gdx.graphics.width / 2 - buttonWidth / 2) / 3
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

		val loadButton = TextButton("Resume", skin)
		loadButton.height = buttonHeight
		loadButton.width = buttonWidth
		loadButton.setPosition(centeredButtonX, centeredButtonY - 100)

		val exitButton = TextButton("Exit", skin)
		exitButton.height = buttonHeight
		exitButton.width = buttonWidth
		exitButton.setPosition(centeredButtonX, centeredButtonY - 100 - buttonHeight - 30)

		loadButton.addListener(object : ChangeListener() {
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

		val label = Label("Paused", skin)
		label.setPosition(Gdx.graphics.width / 2 - label.width / 2, Gdx.graphics.height / 2 - label.height / 2 + 200)

		uiStage.addActor(loadButton)
		uiStage.addActor(exitButton)
		uiStage.addActor(label)

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

	}

	override fun pause() {}
	override fun resume() {}
	override fun hide() {}

	override fun dispose() {
		uiStage.dispose()

	}

}