package com.projekt.sobokan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button_start: Button = findViewById(R.id.button_start)
        val button_quit: Button = findViewById(R.id.button_quit)

        button_start.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java )
            startActivity(intent)
        }

        button_quit.setOnClickListener{
            finish()
            exitProcess(0)
        }
    }
}