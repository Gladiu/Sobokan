package com.projekt.sobokan

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //loadLevel("1.txt")
        val test: GameMap = GameMap(this)
        test.loadLevel("1.txt", application)
        setContentView(R.layout.activity_game)
        setContentView(test)

    }
}