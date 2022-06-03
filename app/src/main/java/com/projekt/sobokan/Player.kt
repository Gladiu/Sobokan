package com.projekt.sobokan

import android.content.Context
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.scale

enum class PlayerDirection{
    North, South, East, West
}

class Player(context: Context, val size: Int): View(context){
    private lateinit var runnable: Runnable
    private var myHandler = Handler(Looper.getMainLooper())
    private val repeatPeriod: Long = 100

    var x: Int = 0
    var y: Int = 0
    var logicX:Int = 0
    var logicY:Int = 0
    private var dir: PlayerDirection = PlayerDirection.South
    var requestedMoveX: Int = 1
    var requestedMoveY: Int = 0
    private val northBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hero_backt).scale(size,size, true)
    private val southBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hero_front).scale(size,size, true)
    private val eastBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hero_rightt).scale(size,size, true)
    private val westBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hero_left).scale(size,size, true)
    private var paint: Paint = Paint()

    fun Draw(canvas: Canvas?) {
        if (dir == PlayerDirection.South)
            canvas?.drawBitmap(southBitmap, x.toFloat(), y.toFloat(), this.paint)
        if ( dir == PlayerDirection.North)
            canvas?.drawBitmap(northBitmap, x.toFloat(), y.toFloat(), this.paint)
        if ( dir == PlayerDirection.East)
            canvas?.drawBitmap(eastBitmap, x.toFloat(), y.toFloat(), this.paint)
        if ( dir == PlayerDirection.West)
            canvas?.drawBitmap(westBitmap, x.toFloat(), y.toFloat(), this.paint)
        var myPaint = Paint();


        paint.setStyle(Paint.Style.STROKE);
        myPaint.setColor(Color.argb(125,255,255, 0));
        myPaint.setStrokeWidth(10F);
        var tempRect = Rect()
        tempRect.left = x + requestedMoveX*size
        tempRect.top = y + requestedMoveY*size
        tempRect.right += tempRect.left + size
        tempRect.bottom += tempRect.top + size
        if ((tempRect.left != x) or (tempRect.top != y)){
            canvas?.drawRect(tempRect, myPaint)
        }
    }

}