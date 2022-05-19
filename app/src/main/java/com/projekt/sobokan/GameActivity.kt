package com.projekt.sobokan

import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class GameActivity : AppCompatActivity() {

    private lateinit var gameState: MutableList<MutableList<Int>>
    var tileSize: Int = 0
    val tileList = mutableListOf<Tile>()
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels;
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels;

    fun loadLevel(levelPath: String){
        gameState.clear()
        try {
            val reader = BufferedReader(InputStreamReader(assets.open(levelPath)))
            var line: String
            Log.e("Reader Stuff", reader.readLine())

            var currentY = 0
            while (reader.readLine().also { line = it } != null) {
                var currentX = 0
                for (character in line){
                    gameState[currentX][currentY] = character.digitToInt()
                    currentX++
                }
                currentY++
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        //loadLevel("1.txt")

        var tempTile = Tile(this)
        tempTile.SetType(1)
        setContentView(tempTile)


    }
}