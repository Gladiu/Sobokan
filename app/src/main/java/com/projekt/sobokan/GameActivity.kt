package com.projekt.sobokan

import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity() {

    val tileList:MutableList<Tile> = mutableListOf<Tile>()
    val maxTilesInLine = 15
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels;
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels;
    var tileSize: Int = (screenWidth*0.8/maxTilesInLine).toInt()

    fun loadLevel(levelPath: String){
        //tileList.clear()

        var currentY = 0
        try{
            application.assets.open(levelPath).bufferedReader().forEachLine {
                var currentX = 0
                for (character in it) {
                    var tempTile: Tile = Tile(this, tileSize)
                    tempTile.SetType(character.digitToInt())
                    tempTile.x = (screenWidth * 0.1 + tileSize * currentX).toDouble()
                    tempTile.y = (screenHeight * 0.1 + tileSize * currentY).toDouble()
                    tileList.add(tempTile)
                    currentX++
                }
                currentY++
            }
        }
        catch (e: Exception){
            println(e.toString())
        }
    }

    fun DrawGame(){
        for (element in tileList) {
            element.Draw(R.layout.cav)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        loadLevel("1.txt")
        DrawGame()

    }
}