package com.example.handgestures

import android.annotation.SuppressLint
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
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val display : TextView = findViewById(R.id.Display)
        lateinit var gesture : GestureDetector
        val intent = Intent(this, MainActivity2::class.java)
        gesture = GestureDetector(this@MainActivity,object : GestureDetector.SimpleOnGestureListener(){
            override fun onLongPress(e: MotionEvent) {
                super.onLongPress(e)
                display.text="Long Press"
                Toast.makeText(this@MainActivity,"Long Press", Toast.LENGTH_SHORT).show()
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                super.onDoubleTap(e)
                startActivity(intent)
                return true
            }

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val diff = e2.x - (e1?.x ?:0f)
                if(abs(diff) > 50 && abs(velocityX) > 100){
                    if(diff > 0){
                        display.text="Right Sweep"
                        Toast.makeText(this@MainActivity,"Right Sweep", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        display.text="Left Sweep"
                        Toast.makeText(this@MainActivity,"Left Sweep", Toast.LENGTH_SHORT).show()
                    }
                }
                return true
            }


        })

       display.setOnTouchListener { v, event ->
            gesture.onTouchEvent(event)
            true
        }

    }

}