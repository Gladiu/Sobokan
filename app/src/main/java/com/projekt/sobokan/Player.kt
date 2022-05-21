package com.projekt.sobokan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.core.graphics.scale

class Player(context: Context, val size: Int): View(context) {
    var x: Int = 0
    var y: Int = 0
    enum class PlayerDirection{
        North, South, East, West
    }
    var dir: PlayerDirection = PlayerDirection.South
    val northBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hero_backt).scale(size,size, true)
    val southBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hero_front).scale(size,size, true)
    val eastBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hero_rightt).scale(size,size, true)
    val westBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hero_left).scale(size,size, true)
    var paint: Paint = Paint()

    fun Draw(canvas: Canvas?) {

        if (dir == PlayerDirection.South)
            canvas?.drawBitmap(southBitmap, x.toFloat(), y.toFloat(), this.paint)
        if ( dir == PlayerDirection.North)
            canvas?.drawBitmap(northBitmap, x.toFloat(), y.toFloat(), this.paint)
        if ( dir == PlayerDirection.East)
            canvas?.drawBitmap(eastBitmap, x.toFloat(), y.toFloat(), this.paint)
        if ( dir == PlayerDirection.West)
            canvas?.drawBitmap(westBitmap, x.toFloat(), y.toFloat(), this.paint)
    }
}