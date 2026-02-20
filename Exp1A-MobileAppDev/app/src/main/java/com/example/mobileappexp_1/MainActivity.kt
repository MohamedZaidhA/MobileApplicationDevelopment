package com.example.mobileappexp_1

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btFontSize : Button = findViewById(R.id.button)
        val font : TextView = findViewById(R.id.Font)
        var btfs : Float = 10.00f
        var btc : Int = 0
        val btColor : Button = findViewById(R.id.button2)
        var btb : Int = 0
        val btbg : Button = findViewById(R.id.button3)
        val layout : LinearLayout = findViewById(R.id.main)
        btFontSize.setOnClickListener {
            btfs = (btfs + 20) % 80
            font.setTextSize(btfs)

        }
        btColor.setOnClickListener {
            btc++
            when (btc % 4) {
                1 -> font.setTextColor(Color.MAGENTA)
                2 -> font.setTextColor(Color.CYAN)
                3 -> font.setTextColor(Color.YELLOW)
                0 -> font.setTextColor(Color.BLACK)


            }
        }
        btbg.setOnClickListener {
            btb++
            when (btb % 4) {
                1 -> layout.setBackgroundColor(Color.RED)
                2 -> layout.setBackgroundColor(Color.GREEN)
                3 -> layout.setBackgroundColor(Color.YELLOW)
                0 -> layout.setBackgroundColor(Color.WHITE)


            }
        }
    }
}