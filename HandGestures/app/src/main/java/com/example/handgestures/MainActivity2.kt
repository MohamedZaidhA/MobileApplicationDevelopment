package com.example.handgestures

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val display : TextView = findViewById(R.id.textView)
        lateinit var gesture : GestureDetector
        val intent = Intent(this, MainActivity::class.java)
        gesture = GestureDetector(this@MainActivity2,object : GestureDetector.SimpleOnGestureListener(){
            override fun onDoubleTap(e: MotionEvent): Boolean {
                super.onDoubleTap(e)
                startActivity(intent)
                return true
            }
        })
        display.setOnTouchListener { v, event ->
            gesture.onTouchEvent(event)
            true
        }
    }
 }