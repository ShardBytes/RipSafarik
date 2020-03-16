package com.shardbytes.ripsafarik.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.shardbytes.ripsafarik.assets.Skin.skin
import com.shardbytes.ripsafarik.components.input.InputCore
import com.shardbytes.ripsafarik.game.MainGame
import com.shardbytes.ripsafarik.tools.SaveManager

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

    private var label = Label("Paused", skin)
    private var loadButton = TextButton("Resume", skin)
    private var saveButton = TextButton("Save", skin)
    private var exitButton = TextButton("Exit", skin)

    init {
        Gdx.input.inputProcessor = uiStage

        loadButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                Gdx.input.inputProcessor = InputCore.reset()
                MainGame.screen = GameScreen
                uiStage.dispose()

            }

        })

        saveButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                SaveManager.save()
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

        uiStage.addActor(loadButton)
        uiStage.addActor(saveButton)
        uiStage.addActor(exitButton)
        uiStage.addActor(label)

        setPositionOfUIElements()

    }

    private fun setPositionOfUIElements() {
        val buttonHeight = uiStage.viewport.screenHeight / 9f
        val buttonWidth = uiStage.viewport.screenWidth / 5f
        val centeredButtonX = uiStage.viewport.screenWidth / 3 / 2 - buttonWidth / 2
        val centeredButtonY = uiStage.viewport.screenHeight / 2 - buttonHeight / 2

        loadButton.height = buttonHeight
        loadButton.width = buttonWidth
        loadButton.setPosition(centeredButtonX, centeredButtonY)

        saveButton.height = buttonHeight
        saveButton.width = buttonWidth
        saveButton.setPosition(centeredButtonX, centeredButtonY - buttonHeight * 1.25f)

        exitButton.height = buttonHeight
        exitButton.width = buttonWidth
        exitButton.setPosition(centeredButtonX, centeredButtonY - buttonHeight * 2.5f)

        label.setPosition(Gdx.graphics.width / 3 / 2 - label.width / 2, Gdx.graphics.height / 2 - label.height / 2 + Gdx.graphics.height / 5)
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
        setPositionOfUIElements()

    }

    override fun pause() {}
    override fun resume() {}
    override fun hide() {}

    override fun dispose() {
        uiStage.dispose()

    }

}