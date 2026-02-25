package com.example.handgestures

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
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
        val imageView : ImageView = findViewById(R.id.imageView)
        var isZoomed = false
        lateinit var gesture : GestureDetector
        val intent = Intent(this, MainActivity::class.java)
        gesture = GestureDetector(this@MainActivity2,object : GestureDetector.SimpleOnGestureListener(){
            override fun onDoubleTap(e: MotionEvent): Boolean {
                super.onDoubleTap(e)
                if (isZoomed) {
                    imageView.animate().scaleX(1f).scaleY(1f).setDuration(300).start()
                    Toast.makeText(this@MainActivity2,"Zoom Out", Toast.LENGTH_SHORT).show()
                } else {
                    imageView.animate().scaleX(2f).scaleY(2f).setDuration(300).start()
                    Toast.makeText(this@MainActivity2,"Zoom In", Toast.LENGTH_SHORT).show()
                }
                isZoomed = !isZoomed
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                super.onLongPress(e)
                Toast.makeText(this@MainActivity2,"Long Press", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        })
        imageView.setOnTouchListener { v, event ->
            gesture.onTouchEvent(event)
            true
        }
    }
 }