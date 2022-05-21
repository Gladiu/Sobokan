package com.projekt.sobokan

import android.content.res.Resources
import android.graphics.Canvas
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.*
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity() {

    lateinit var map: GameMap
    lateinit var player: Player
    lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //loadLevel("1.txt")
        map = GameMap(this)
        gameView = GameView(this)
        map.loadLevel("1.txt", application)
        player = Player(this, map.tileSize)
        gameView.player = player
        gameView.gameMap = map
        setContentView(gameView)

    }
}