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

    //private lateinit var gameState: MutableList<MutableList<Int>>
    private lateinit var tileList: MutableList<MutableList<Tile>>
    //val tileList = mutableListOf<Tile>()
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels;
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels;
    val gameRectangle: Rect = Rect(screenWidth*0.1, screenHeight*0.1, screenWidth*0.9, screenHeight*0.5)
    var tileSize: Int = 0

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
                    tileList[currentX][currentY] = character.digitToInt()
                    tileList[currentX][currentY].SetType(character.digitToInt())
                    currentX++
                }
                currentY++
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    fun DrawGame(){
        for (subList in tileList) {
            for (element in subList){
                setContentView(element)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        DrawGame()

    }
}