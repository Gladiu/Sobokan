package com.projekt.sobokan

import android.content.Context
import android.graphics.Canvas
import android.view.View

class GameView(context: Context) : View(context) {
    lateinit var gameMap: GameMap
    lateinit var player: Player

    override fun onDraw(canvas: Canvas?) {
        gameMap.Draw(canvas)
        player.Draw(canvas)
    }
}