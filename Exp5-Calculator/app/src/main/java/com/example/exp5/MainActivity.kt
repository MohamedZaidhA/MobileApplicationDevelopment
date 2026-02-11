package com.example.exp5

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val v1 : EditText = findViewById(R.id.Value1)
        val v2 : EditText = findViewById(R.id.Value2)

        val add : Button = findViewById(R.id.add)
        val sub : Button = findViewById(R.id.sub)
        val mul : Button = findViewById(R.id.Mul)
        val div : Button = findViewById(R.id.Divide)
        val mod : Button = findViewById(R.id.mod)

        val res : TextView = findViewById(R.id.Result)
        add.setOnClickListener {
            var a : Double = v1.text.toString().toDouble()
            var b: Double = v2.text.toString().toDouble()
            var c : String = String.format("%.2f",a+b)
            res.text="Ans: $c"
        }
        sub.setOnClickListener {
            var a : Double = v1.text.toString().toDouble()
            var b: Double = v2.text.toString().toDouble()
            var c : String = String.format("%.2f",a-b)
            res.text="Ans: $c"
        }
        mul.setOnClickListener {
            var a : Double = v1.text.toString().toDouble()
            var b: Double = v2.text.toString().toDouble()
            var c : String = String.format("%.2f",a*b)
            res.text="Ans: $c"
        }
        mod.setOnClickListener {
            var a : Double = v1.text.toString().toDouble()
            var b: Double = v2.text.toString().toDouble()
            var c : String = String.format("%.2f",a%b)
            res.text="Ans: $c"
        }
        div.setOnClickListener {
            var a : Double = v1.text.toString().toDouble()
            var b: Double = v2.text.toString().toDouble()
            var c : String = String.format("%.2f",a/b)
            res.text="Ans: $c"
        }
    }
}