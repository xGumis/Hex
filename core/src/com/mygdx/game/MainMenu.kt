package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.onClick

class MainMenu(val game : Hex) : Screen {
    private val stage = Stage(ScreenViewport())
    init{
        val gen = FreeTypeFontGenerator(Gdx.files.internal("Roboto-Bold.ttf"))
        val param = FreeTypeFontGenerator.FreeTypeFontParameter()
        param.size = Gdx.graphics.height/6f.toInt()
        param.color = myColors.finish
        val font = gen.generateFont(param)
        gen.dispose()
        val labstyle = Label.LabelStyle()
        labstyle.font = font
        val button1 = TextButton("1 Gracz",game.skin)
        button1.label.style = labstyle
        button1.setSize(Gdx.graphics.width/2f,Gdx.graphics.height/4f)
        button1.setPosition(Gdx.graphics.width/4f,Gdx.graphics.height*(3/7f))
        button1.onClick { game.screen = HexGame(game,1,9) }
        stage.addActor(button1)
        val button2 = TextButton("2 Graczy",game.skin)
        button2.label.style = labstyle
        button2.setSize(Gdx.graphics.width/2f,Gdx.graphics.height/4f)
        button2.setPosition(Gdx.graphics.width/4f,Gdx.graphics.height/7f)
        button2.onClick { game.screen = HexGame(game,0,9) }
        stage.addActor(button2)
        val label = Label("Hex",labstyle)
        label.setAlignment(Align.center)
        label.setSize(Gdx.graphics.width/2f,Gdx.graphics.height/4f)
        label.setPosition(Gdx.graphics.width/4f,Gdx.graphics.height*(5/7f))
        stage.addActor(label)
    }
    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        myColors.bckgrnd()
        stage.act()
        stage.draw()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
        stage.dispose()
    }

    override fun hide() {
    }
}