package com.mygdx.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import ktx.actors.onClick
import kotlin.collections.ArrayList
import kotlin.math.sqrt

class HexMap(x:Float,y:Float,height:Float,val num:Int, mode:Int) {
    var color = myColors.player1
    var winner = myColors.finish
    var hexActors : ArrayList<ArrayList<HexActor>>
    var rowArray = MutableList(num){it}
    var occArr = Array(num){MutableList(num){it}}
    var chckArr = Array(num){Array(num){false}}
    var sidesOccupied = arrayOf(false,false)
    init {
        val h = (height-((height/num)*(1f/4f)))/num*(4f/3f)
        val w = h*sqrt(3f)/2
        var X : Float
        var Y = y+h/4f
        hexActors = ArrayList()
        for(i in 1..num){
            val tmp = ArrayList<HexActor>()
            for(j in 1..num){
                X = (j-1)*w + if(i%2 == 0){w/2}else{0f}
                val hex = HexActor(Vector2(x+X,Y),h)
                hex.onClick {
                    if(!hex.isOccupied()&&color!=myColors.finish){
                        hex.color = color
                        clearArr()
                        occupy(i-1,j-1)
                        checkPath(i-1,j-1)
                        changePlayer()
                        if(mode==1 && color==myColors.player2) computr()
                    }
                }
                tmp.add(hex)
            }
            Y += (3f/4f)*h
            hexActors.add(tmp)
        }
    }
    //region Game functions
    private fun changePlayer(){
        if(color == myColors.player1) color = myColors.player2
        else if(color == myColors.player2) color = myColors.player1
    }
    private fun computr(){
        rowArray.shuffle()
        val i = rowArray.first()
        occArr[i].shuffle()
        val j = occArr[i].first()
        occupy(i,j)
        hexActors[i][j].color = color
        clearArr()
        checkPath(i,j)
        changePlayer()
    }
    private fun occupy(i: Int,j: Int){
        occArr[i].remove(j)
        if(occArr[i].isEmpty())rowArray.remove(i)
    }
    private fun clearArr(){
        chckArr = Array(num){Array(num){false}}
        sidesOccupied = arrayOf(false,false)
    }
    private fun askOther(i: Int,j: Int){
        if(j!=0)checkOther(i,j-1)
        if(j!=num-1)checkOther(i,j+1)
        if(i!=num-1)checkOther(i+1,j)
        if(i!=0)checkOther(i-1,j)
        when(i%2){
            0 -> {
                if(j!=0){
                    if(i!=num-1)checkOther(i+1,j-1)
                    if(i!=0)checkOther(i-1,j-1)
                }
            }
            1 ->{
                if(j!=num-1){
                    if(i!=num-1)checkOther(i+1,j+1)
                    if(i!=0)checkOther(i-1,j+1)
                }
            }
        }
    }
    private fun checkOther(i: Int, j: Int){
        if(hexActors[i][j].color==color)checkPath(i,j)
    }
    private fun checkPath(i: Int, j: Int){
        if(!chckArr[i][j]){
            chckArr[i][j] = true
            when(color){
                Color.BLUE -> {
                    if(j==0)sidesOccupied[0]=true
                    if(j==num-1)sidesOccupied[1]=true
                }
                Color.RED -> {
                    if(i==0)sidesOccupied[0]=true
                    if(i==num-1)sidesOccupied[1]=true
                }
            }
            if(sidesOccupied[0]&&sidesOccupied[1]){winner=color;color=myColors.finish}
            askOther(i,j)
        }
    }

    //endregion
    fun NewGame(){
        color = myColors.player1
        hexActors.forEach{ it.forEach { it.Clear() }}
    }
}