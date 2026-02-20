package com.example.exp4_celciustofarenheit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val top : EditText = findViewById(R.id.input)
        val bottom : TextView = findViewById(R.id.output)
        val convert : Button = findViewById(R.id.button)
        val swap : TextView = findViewById(R.id.swap)

        var count : Int = 0

        swap.setOnClickListener {
            count++
            if(count%2==1)
            {
                top.hint="Enter Temperature in °F".toString()
                top.text.clear()
                bottom.text="Temperature in °C".toString()

            }else{
                top.hint="Enter Temperature in °C".toString()
                top.text.clear()
                bottom.text="Temperature in °F".toString()

            }
        }

        convert.setOnClickListener {
            var input : Double = top.text.toString().toDouble()
            if(count%2==1)
            {
                var ans : String = String.format("%.2f",((input-32)*5/9))
                bottom.text="$ans °C".toString()

            }else{
                var ans : String = String.format("%.2f",((input*1.8)+32))
                bottom.text="$ans °F".toString()

            }
        }


    }
}