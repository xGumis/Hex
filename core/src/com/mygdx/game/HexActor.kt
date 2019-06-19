package com.mygdx.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.EarClippingTriangulator
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.utils.ShortArray
import kotlin.math.sqrt




class HexActor(originPoint: Vector2,h: Float): Actor() {

    val vertices : FloatArray
    val indices : ShortArray
    val sr = ShapeRenderer()

    init {
        color = myColors.empty
        x = originPoint.x
        y = originPoint.y
        height = h
        vertices = floatArrayOf(
                x,y,
                x,y+(height/2),
                x+(height* sqrt(3f)/4),y+(height*(3f/4f)),
                x+(height* sqrt(3f)/2),y+(height/2),
                x+(height* sqrt(3f)/2),y,
                x+(height* sqrt(3f)/4),y-(height/4)
        )
        val ear = EarClippingTriangulator()
        indices = ear.computeTriangles(vertices)
    }
    override fun hit(x: Float, y: Float, touchable: Boolean): Actor? {
        if(touchable&&getTouchable() == Touchable.enabled){
            val a = height/2
            if(
                    (
                            (a* sqrt(3f)/2>x && x>0)&&
                                    (y< sqrt(3f)*x/3+a && y>sqrt(3f)*x/(-3))
                            )||
                    (
                            ((x>a* sqrt(3f)/2 && x<a* sqrt(3f))&&
                                    (y<sqrt(3f)*x/(-3)+2*a && y>sqrt(3f)*x/3-a)
                            )
                    ))return this
        }
        return null
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        batch?.end()
        sr.begin(ShapeRenderer.ShapeType.Filled)
        sr.color = color
        var i = 0
        while (i < indices.size - 2) {
            val x1 = vertices[indices.get(i) * 2]
            val y1 = vertices[indices.get(i) * 2 + 1]

            val x2 = vertices[indices.get(i + 1) * 2]
            val y2 = vertices[indices.get(i + 1) * 2 + 1]

            val x3 = vertices[indices.get(i + 2) * 2]
            val y3 = vertices[indices.get(i + 2) * 2 + 1]

            sr.triangle(x1, y1, x2, y2, x3, y3)
            i += 3
        }
        sr.end()
        sr.begin(ShapeRenderer.ShapeType.Line)
        sr.color = Color.GREEN
        sr.polygon(vertices)
        sr.end()
        batch?.begin()
    }

    fun isOccupied() : Boolean{
        return color != myColors.empty
    }
    fun Clear(){
        color = myColors.empty
    }
}