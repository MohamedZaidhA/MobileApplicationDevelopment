package com.example.bmi_exp3

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
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
        val height : EditText = findViewById(R.id.Height)
        val weight : EditText = findViewById(R.id.weight)
        val display : TextView = findViewById(R.id.Display)
        val display2 : TextView = findViewById(R.id.Display2)
        val calculate : Button = findViewById(R.id.button)
        val inCm : RadioButton = findViewById(R.id.inCm)
        calculate.setOnClickListener {
            var H : Double = height.text.toString().toDouble()
            var W : Double = weight.text.toString().toDouble()
            var B : Double = W /(H * H)
            if (inCm.isChecked)
            {
                B = B * 10000
            }
            val BMI = String.format("%.2f",B)
            display.text="Your BMI is $BMI"
            if (B<18.5) {
                display2.text="You are UnderWieght"
                display2.setTextColor(Color.YELLOW)
            }else if (B< 25){
                display2.text="You are Normal"
                display2.setTextColor(Color.GREEN)
            }else{
                display2.text="You are OverWeight"
                display2.setTextColor(Color.RED)
            }
        }
    }
}