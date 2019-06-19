package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.actors.onClick
import kotlin.math.sqrt

class HexGame(val game : Hex, mode: Int, numOfHexes: Int) : Screen {

    private val stage = Stage(ScreenViewport())
    private val sr = ShapeRenderer()
    private val sizex = Gdx.graphics.height-100f
    private val sizey = sizex* (2f/ sqrt(3f))
    private val map = HexMap((Gdx.graphics.width-sizey)/2f,50f,sizex,numOfHexes,mode)
    private val end : Label
    private val bg : Image
    private val tex : Texture
    private val pixmap : Pixmap
    private val gen = FreeTypeFontGenerator(Gdx.files.internal("Roboto-Bold.ttf"))

    init{
        var param = FreeTypeFontGenerator.FreeTypeFontParameter()
        param.size = Gdx.graphics.height/22f.toInt()
        param.color = myColors.finish
        var font = gen.generateFont(param)
        val butstyle = Label.LabelStyle()
        butstyle.font = font
        val back = TextButton("Menu",game.skin)
        back.label.style = butstyle
        back.setSize(Gdx.graphics.width/10f, Gdx.graphics.height/10f)
        back.setPosition(Gdx.graphics.height/15f, Gdx.graphics.height/15f)
        back.onClick { game.screen = MainMenu(game) }
        stage.addActor(back)
        val reset = TextButton("Restart",game.skin)
        reset.label.style = butstyle
        reset.setSize(Gdx.graphics.width/10f, Gdx.graphics.height/10f)
        reset.setPosition(Gdx.graphics.height/15f, Gdx.graphics.height*(3f/15f))
        reset.onClick {
            map.NewGame()
            stage.clear()
            map.hexActors.forEach { it.forEach { stage.addActor(it) } }
            stage.addActor(reset)
            stage.addActor(back)
        }
        pixmap = Pixmap((Gdx.graphics.width*(13/15f)).toInt(),(Gdx.graphics.height/2f).toInt(),Pixmap.Format.RGBA8888)
        pixmap.setColor(0f,0f,0f,1f)
        pixmap.fillRectangle(0,0,pixmap.width,pixmap.height)
        pixmap.setColor(myColors.finish)
        pixmap.drawRectangle(0,0,pixmap.width,pixmap.height)
        tex = Texture(pixmap)
        pixmap.dispose()
        bg = Image(tex)
        bg.setSize(Gdx.graphics.width*(13/15f),Gdx.graphics.height/2f)
        bg.setPosition(Gdx.graphics.height*(1/15f),Gdx.graphics.height/3f)
        param.size = Gdx.graphics.width/12f.toInt()
        font = gen.generateFont(param)
        val labstyle = Label.LabelStyle()
        labstyle.font = font
        end = Label("WYGRYWA",labstyle)
        end.setAlignment(Align.center)
        end.setSize(Gdx.graphics.width/2f,Gdx.graphics.height/2f)
        end.setPosition(Gdx.graphics.height*(2/15f),Gdx.graphics.height/3f)
        stage.addActor(reset)
        map.hexActors.forEach { it.forEach { stage.addActor(it) } }


    }
    override fun hide() {
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        myColors.bckgrnd()
        if(map.color == myColors.finish)
        {
            stage.addActor(bg)
            stage.addActor(end)
        }
        sr.begin(ShapeRenderer.ShapeType.Filled)
        sr.color = Color.RED
        sr.rect((Gdx.graphics.width-sizey)/2f,20f,sizey,10f)
        sr.rect((Gdx.graphics.width-sizey)/2f,sizex+70f,sizey,10f)
        sr.color = Color.BLUE
        sr.rect((Gdx.graphics.width-sizey)/2f-20f,50f,10f,sizex)
        sr.rect((Gdx.graphics.width+sizey)/2f+40f,50f,10f,sizex)
        sr.color = map.color
        sr.circle(Gdx.graphics.height*(2f/15f),Gdx.graphics.height*(13f/15f),Gdx.graphics.height/15f)
        sr.end()
        stage.act()
        stage.draw()
        if(map.color == myColors.finish)
        {
            sr.begin(ShapeRenderer.ShapeType.Filled)
            sr.color = map.winner
            sr.circle(Gdx.graphics.width*3/4f,Gdx.graphics.height*(7/12f),Gdx.graphics.height/6f)
            sr.end()
        }
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
        stage.dispose()
        sr.dispose()
        gen.dispose()
    }
}