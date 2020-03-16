package com.shardbytes.ripsafarik.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.shardbytes.ripsafarik.assets.Skin.skin
import com.shardbytes.ripsafarik.assets.Textures
import com.shardbytes.ripsafarik.components.input.InputCore
import com.shardbytes.ripsafarik.game.MainGame

class MenuScreen : Screen {

    private val uiStage = Stage(
            ScreenViewport()

    )

    private val playButton = TextButton("New game", skin)
    private val loadButton = TextButton("Load game", skin)
    private val exitButton = TextButton("Exit", skin)

    private val label = Label("RipSafarik", skin)

    private val safarik = Image(Textures.Overlay["safarik"])

    init {
        Gdx.input.inputProcessor = uiStage

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

        uiStage.addActor(playButton)
        uiStage.addActor(loadButton)
        uiStage.addActor(exitButton)
        uiStage.addActor(safarik)
        uiStage.addActor(label)

        setPositionOfUIElements()

    }

    private fun setPositionOfUIElements() {
        val buttonHeight = uiStage.viewport.screenHeight / 9f
        val buttonWidth = uiStage.viewport.screenWidth / 1.5f
        val centeredButtonX = Gdx.graphics.width / 2 - buttonWidth / 2
        val centeredButtonY = Gdx.graphics.height / 2 - buttonHeight / 2

        playButton.height = buttonHeight
        playButton.width = buttonWidth
        playButton.setPosition(centeredButtonX, centeredButtonY)

        loadButton.height = buttonHeight
        loadButton.width = buttonWidth
        loadButton.setPosition(centeredButtonX, centeredButtonY - buttonHeight * 1.5f)

        exitButton.height = buttonHeight
        exitButton.width = buttonWidth
        exitButton.setPosition(centeredButtonX, centeredButtonY - buttonHeight * 3f)

        label.setPosition(Gdx.graphics.width / 2 - label.width / 2, Gdx.graphics.height / 2 - label.height / 2 + buttonHeight * 3.5f)

        safarik.width = centeredButtonY / 2f
        safarik.height = centeredButtonY / 2f
        safarik.setPosition(Gdx.graphics.width / 2 - safarik.width / 2, Gdx.graphics.height / 2 - safarik.height / 2 + buttonHeight * 2f)
    }

    override fun show() {}

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        uiStage.act(delta)
        uiStage.draw()

    }

    override fun resize(width: Int, height: Int) {
        uiStage.viewport.update(width, height, true)
        setPositionOfUIElements()

    }

    override fun pause() {}
    override fun resume() {}
    override fun hide() {}

    override fun dispose() {
        uiStage.dispose()

    }

}