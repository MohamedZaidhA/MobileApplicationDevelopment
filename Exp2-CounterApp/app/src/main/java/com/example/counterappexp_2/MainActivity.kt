package com.example.counterappexp_2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val up : Button = findViewById(R.id.up)
        val down : Button = findViewById(R.id.down)
        val reset : Button = findViewById(R.id.reset)
        var count : Int = 0
        val font : TextView = findViewById(R.id.num)
        up.setOnClickListener {
            count++
            font.text="$count"
        }
        down.setOnClickListener {
            count--
            font.text="$count"
        }
        reset.setOnClickListener {
            count = 0
            font.text="$count"
        }
    }
}