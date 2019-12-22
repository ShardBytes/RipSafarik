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
import com.shardbytes.ripsafarik.MainGame
import com.shardbytes.ripsafarik.assets.Textures

class MenuScreen(private val mainScreen: MainGame) : Screen {

	private val uiStage: Stage

	private val buttonHeight = 50
	private val buttonWidth = 500
	private val centeredButtonX = Gdx.graphics.width / 2 - buttonWidth / 2
	private val centeredButtonY = Gdx.graphics.height / 2 - buttonHeight / 2

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

		val loadButton = TextButton("Play", skin)
		loadButton.height = buttonHeight.toFloat()
		loadButton.width = buttonWidth.toFloat()
		loadButton.setPosition(centeredButtonX.toFloat(), centeredButtonY - 100.toFloat())

		val exitButton = TextButton("Exit", skin)
		exitButton.height = buttonHeight.toFloat()
		exitButton.width = buttonWidth.toFloat()
		exitButton.setPosition(centeredButtonX.toFloat(), centeredButtonY - 100 - buttonHeight - 30.toFloat())

		loadButton.addListener(object : ChangeListener() {
			override fun changed(event: ChangeEvent, actor: Actor) {
				mainScreen.screen = GameScreen
				uiStage.dispose()
			}
		})
		exitButton.addListener(object : ChangeListener() {
			override fun changed(event: ChangeEvent, actor: Actor) {
				Gdx.app.exit()
			}
		})

		val label = Label("RipSafarik", skin)
		label.setPosition(Gdx.graphics.width / 2 - label.width / 2, Gdx.graphics.height / 2 - label.height / 2 + 200)

		val safarik = Image(Textures.Overlay["safarik"])
		safarik.width = 100F
		safarik.height = 100F
		safarik.setPosition(Gdx.graphics.width / 2 - safarik.width / 2, Gdx.graphics.height / 2 - safarik.height / 2 + 90)

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

	init {
		val viewport = ScalingViewport(Scaling.fit,Gdx.graphics.width.toFloat(),Gdx.graphics.height.toFloat())
		uiStage = Stage(viewport)
		Gdx.input.inputProcessor = uiStage
		addUIElements()
	}
}