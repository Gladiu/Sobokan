package com.projekt.sobokan

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.abs


class GameActivity : AppCompatActivity(), SensorEventListener {

    lateinit var map: GameMap
    lateinit var player: Player
    lateinit var gameView: GameView


    private val TAG = "GameActivity"

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var magnetometer: Sensor
    private var mGravity: FloatArray? = null
    private var mGeomagnetic: FloatArray? = null

    private lateinit var runnable: Runnable
    private var myHandler = Handler(Looper.getMainLooper())
    private val repeatPeriod: Long = 50

    private var lastScreenTap: Long = 0
    fun ScreenTapped(): Boolean {

        if (System.currentTimeMillis() > lastScreenTap+200) {
            lastScreenTap = System.currentTimeMillis()
            if (map.CanMoveToTile(
                    player.requestedMoveX + player.logicX,
                    player.requestedMoveY + player.logicY
                )
            ) {
                player.x += (map.tileSize) * player.requestedMoveX
                player.y += (map.tileSize) * player.requestedMoveY
                player.logicX += player.requestedMoveX
                player.logicY += player.requestedMoveY
                println("asd")
                return true
            }
        }
        return false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //loadLevel("1.txt")
        map = GameMap(this)
        gameView = GameView(this)
        map.loadLevel("1.txt", application)
        player = Player(this, map.tileSize)
        map.ResetPlayer(player)
        gameView.player = player
        gameView.gameMap = map


        // Setup the sensors
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager;
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer == null) {
            Log.d(TAG, "accelerometer is null");
        }
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magnetometer == null) {
            Log.d(TAG, "magnetometer is null");
        }
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);


        runnable = Runnable {

            setContentView(gameView)
            myHandler.postDelayed(runnable, repeatPeriod)

        }
        myHandler.postDelayed(runnable, repeatPeriod)

        gameView.setOnTouchListener { v, event -> ScreenTapped()  }

    }


    override fun onSensorChanged(event: SensorEvent) {
        //Log.d(TAG, "onSensorChanged()");
        //Log.d(TAG, "onSensorChanged()");
        if (event.values == null) {
            Log.w(TAG, "event.values is null")
            return
        }
        val sensorType = event.sensor.type
        when (sensorType) {
            Sensor.TYPE_ACCELEROMETER -> mGravity = event.values
            Sensor.TYPE_MAGNETIC_FIELD -> mGeomagnetic = event.values
            else -> {
                Log.w(TAG, "Unknown sensor type $sensorType")
                return
            }
        }
        if (mGravity == null) {
            Log.w(TAG, "mGravity is null")
            return
        }

        if (mGeomagnetic == null) {
            Log.w(TAG, "mGeomagnetic is null")
            return
        }
        val R = FloatArray(9)
        if (!SensorManager.getRotationMatrix(R, null, mGravity, mGeomagnetic)) {
            Log.w(TAG, "getRotationMatrix() failed")
            return
        }

        val orientation = FloatArray(9)
        SensorManager.getOrientation(R, orientation)
        // Orientation contains: azimuth, pitch and roll - we'll use roll
        // Orientation contains: azimuth, pitch and roll - we'll use roll
        val pitch = orientation[1]
        val pitchDeg = Math.round(Math.toDegrees(pitch.toDouble())).toInt()
        val roll = orientation[2]
        val rollDeg = Math.round(Math.toDegrees(roll.toDouble())).toInt()

        if ( (abs(pitchDeg) > 10) ){
            player.requestedMoveY = if (pitchDeg > 0) -1 else 1
        }
        else {
            player.requestedMoveY = 0
        }
        if ( (abs(rollDeg) > 10) ){
            player.requestedMoveX = if (rollDeg > 0) 1 else -1
        }
        else {
            player.requestedMoveX = 0
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}