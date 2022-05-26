package com.projekt.sobokan

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.View
import androidx.core.graphics.scale

class GameMap(context: Context) : View(context) {

    class Tile(val x: Int, val y: Int, val type: Int, val logicX: Int, val logicY: Int)
    class Crate(var x: Int, var y: Int, var logicX: Int, var logicY: Int, var onSpot: Boolean)
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels


    var paint: Paint = Paint()

    val tileInfo:MutableList<Tile> = mutableListOf<Tile>()
    val crateInfo:MutableList<Crate> = mutableListOf<Crate>()
    lateinit var spawnTile:Tile

    val maxTilesInLine = 7
    val tileSize: Int = (screenWidth*0.8/maxTilesInLine).toInt()

    val floorBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.floor).scale(tileSize,tileSize, true)
    val wallBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.wall).scale(tileSize,tileSize, true)
    val targetBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.target).scale(tileSize,tileSize, true)
    val crateBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.crate).scale(tileSize,tileSize, true)


    fun loadLevel(levelPath: String, application: Application){
        tileInfo.clear()
        var currentY = 0
        try{
            application.assets.open(levelPath).bufferedReader().forEachLine {
                var currentX = 0
                for (character in it) {
                    if(character.digitToInt() == 4) {
                        var tempTile: Tile = Tile(
                            (screenWidth * 0.1 + tileSize * currentX).toInt(),
                            (screenHeight * 0.1 + tileSize * currentY).toInt(),
                            1,
                            currentX,
                            currentY
                        )
                        var tempCrate: Crate = Crate(
                            (screenWidth * 0.1 + tileSize* currentX).toInt(),
                            (screenHeight * 0.1 +  tileSize* currentY).toInt(),
                            currentX,
                            currentY,
                            false
                        )
                        tileInfo.add(tempTile)
                        crateInfo.add(tempCrate)
                    }
                    if(character.digitToInt() == 5) {
                        var tempTile: Tile = Tile(
                            (screenWidth * 0.1 + tileSize * currentX).toInt(),
                            (screenHeight * 0.1 + tileSize * currentY).toInt(),
                            1,
                            currentX,
                            currentY
                        )
                        spawnTile = tempTile
                        tileInfo.add(tempTile)
                    }
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
    fun CheckMove(fromX:Int, fromY:Int, requestedX:Int, requestedY: Int) : Boolean{
        val requestedTile:Tile? = tileInfo.find { (it.logicX == requestedX) and (it.logicY == requestedY)}
        val searchedCrate:Crate? = crateInfo.find { (it.logicX == requestedX) and (it.logicY == requestedY)}
        if (searchedCrate !=null){
            val newCrateTile:Tile? = tileInfo.find { (it.logicX == (requestedX + (requestedX-fromX))) and (it.logicY == (requestedY + (requestedY-fromY)))}
            if ((newCrateTile != null) and ((newCrateTile?.type == 4) or (newCrateTile?.type == 1) or (newCrateTile?.type == 3))){
                searchedCrate.x = newCrateTile?.x!!
                searchedCrate.y = newCrateTile?.y!!
                searchedCrate.logicX = newCrateTile?.logicX!!
                searchedCrate.logicY = newCrateTile?.logicY!!
                searchedCrate.onSpot = newCrateTile?.type == 3
                return true
            }
            else{
                return false
            }
        }
        return (requestedTile != null) and ((requestedTile?.type == 4) or (requestedTile?.type == 1) or (requestedTile?.type == 3))
    }

    fun ResetPlayer( player: Player){
        player.x = spawnTile.x
        player.y = spawnTile.y
        player.logicX = spawnTile.logicX
        player.logicY = spawnTile.logicY
    }

    fun CheckGameEnd() : Boolean{
        return crateInfo.find{ !it.onSpot } == null
    }
    fun Draw(canvas: Canvas?) {

        for (tile in tileInfo){
            if ((tile.type == 1))
                canvas?.drawBitmap(floorBitmap, tile.x.toFloat(), tile.y.toFloat(), this.paint)
            if (tile.type == 2)
                canvas?.drawBitmap(wallBitmap, tile.x.toFloat(), tile.y.toFloat(), this.paint)
            if (tile.type == 3)
                canvas?.drawBitmap(targetBitmap, tile.x.toFloat(), tile.y.toFloat(), this.paint)
        }
        for (crate in crateInfo){
            canvas?.drawBitmap(crateBitmap, crate.x.toFloat(), crate.y.toFloat(), this.paint)
        }
    }
}

