package com.projekt.sobokan

import android.content.Context
import android.graphics.*
import android.view.View


enum class TileType{
    WALL, FLOOR, TARGET
}

class Tile(context: Context, desiredSize: Int) : View(context) {
    var x = 0.0
    var y = 0.0
    var size = desiredSize
    var type: Int = 0
    var paint: Paint = Paint()
    var bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.wall) // TODO create default texture

    fun SetType(desiredType: Int) {
        this.type = desiredType
        if (type == 0){
            bitmap = BitmapFactory.decodeResource(resources, R.drawable.wall)
        }
        if (type == 1){
            bitmap = BitmapFactory.decodeResource(resources, R.drawable.floor)
        }
        if (type == 2){
            bitmap = BitmapFactory.decodeResource(resources, R.drawable.target)
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, size, size, false)
    }

    public override fun onDraw(canvas: Canvas) {
        this.paint.color = Color.RED

        canvas.drawBitmap(this.bitmap, this.x.toFloat(), this.y.toFloat(), this.paint)
    }
}

