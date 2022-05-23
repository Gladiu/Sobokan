package com.projekt.sobokan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.graphics.scale

class Player(context: Context, val size: Int): View(context) {
    private lateinit var runnable: Runnable
    private var myHandler = Handler(Looper.getMainLooper())
    private val repeatPeriod: Long = 100

    var x: Int = 0
    var y: Int = 0
    enum class PlayerDirection{
        North, South, East, West
    }
    private var dir: PlayerDirection = PlayerDirection.South
    private val northBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hero_backt).scale(size,size, true)
    private val southBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hero_front).scale(size,size, true)
    private val eastBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hero_rightt).scale(size,size, true)
    private val westBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hero_left).scale(size,size, true)
    private var paint: Paint = Paint()

    init{
        runnable = Runnable {

            x = SensorManager.AXIS_X
            myHandler.postDelayed(runnable, repeatPeriod)

        }
        myHandler.postDelayed(runnable, repeatPeriod)
    }

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