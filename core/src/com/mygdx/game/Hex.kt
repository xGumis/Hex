package com.mygdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Skin

class Hex : Game() {
    lateinit var skin : Skin
    override fun create() {
        skin = Skin(Gdx.files.internal("skin/tracer-ui.json"))
        this.setScreen(MainMenu(this))
    }
}
