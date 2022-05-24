package com.projekt.sobokan

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.View
import androidx.core.graphics.scale


class GameMap(context: Context) : View(context) {

    class Tile(val x: Int, val y: Int, val type: Int, val logicX: Int, val logicY: Int)
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    var paint: Paint = Paint()

    val tileInfo:MutableList<Tile> = mutableListOf<Tile>()

    val maxTilesInLine = 7
    val tileSize: Int = (screenWidth*0.8/maxTilesInLine).toInt()

    val wallBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.wall).scale(tileSize,tileSize, true)
    val floorBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.floor).scale(tileSize,tileSize, true)
    val targetBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.target).scale(tileSize,tileSize, true)


    fun loadLevel(levelPath: String, application: Application){
        tileInfo.clear()
        var currentY = 0
        try{
            application.assets.open(levelPath).bufferedReader().forEachLine {
                var currentX = 0
                for (character in it) {
                    if(character.digitToInt() != 0) {
                        var tempTile: Tile = Tile(
                            (screenWidth * 0.1 + tileSize * currentX).toInt(),
                            (screenHeight * 0.1 + tileSize * currentY).toInt(),
                            character.digitToInt(),
                            currentX,
                            currentY
                        )
                        tileInfo.add(tempTile)
                    }
                    currentX++
                }
                currentY++
            }
        }
        catch (e: Exception){
            println(e.toString())
        }
    }
    fun CanMoveToTile(requestedX:Int, requestedY: Int) : Boolean{
        val searchedTile:Tile? = tileInfo.find { (it.logicX == requestedX) and (it.logicY == requestedY)}
        return (searchedTile != null) and ((searchedTile?.type == 4) or (searchedTile?.type == 1))
    }

    fun ResetPlayer( player: Player){
        val searchedTile:Tile? = tileInfo.find { (it.type == 4)}
        if (searchedTile?.type == 4){
            player.x = searchedTile.x
            player.y = searchedTile.y
            player.logicX = searchedTile.logicX
            player.logicY = searchedTile.logicY
        }
    }
    fun Draw(canvas: Canvas?) {

        for (tile in tileInfo){
            if ((tile.type == 1) or (tile.type == 4))
                canvas?.drawBitmap(floorBitmap, tile.x.toFloat(), tile.y.toFloat(), this.paint)
            if (tile.type == 2)
                canvas?.drawBitmap(wallBitmap, tile.x.toFloat(), tile.y.toFloat(), this.paint)
            if (tile.type == 3)
                canvas?.drawBitmap(targetBitmap, tile.x.toFloat(), tile.y.toFloat(), this.paint)
        }
    }
}

