package com.example.exp6_scientific_calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.function.Function
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    private lateinit var display: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        display = findViewById(R.id.display)

        // Map of button IDs to the text they should append
        val buttons = mapOf(
            R.id.num0 to "0", R.id.num1 to "1", R.id.num2 to "2", R.id.num3 to "3",
            R.id.num4 to "4", R.id.num5 to "5", R.id.num6 to "6", R.id.num7 to "7",
            R.id.num8 to "8", R.id.num9 to "9", R.id.dot to ".", R.id.add to "+",
            R.id.sub to "-", R.id.mul to "*", R.id.div to "/", R.id.sqrt to "√",
            R.id.sin to "sin", R.id.cos to "cos", R.id.tan to "tan", R.id.log to "log"
        )

        buttons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener {
                display.append(value)
            }
        }

        findViewById<Button>(R.id.clear).setOnClickListener {
            display.text = ""
        }

        findViewById<Button>(R.id.equal).setOnClickListener {
            calculateResult()
        }
    }

    private fun calculateResult() {
        val rawInput = display.text.toString()
        if (rawInput.isEmpty()) return

        try {
            // Replace the UI symbol with the function name expected by exp4j
            val input = rawInput.replace("√", "sqrt")

            // Define custom functions to handle DEGREES for trigonometric operations
            val sinDeg = object : Function("sin", 1) {
                override fun apply(vararg args: Double): Double = round((Math.sin(Math.toRadians(args[0]))*10000))/10000
            }
            val cosDeg = object : Function("cos", 1) {
                override fun apply(vararg args: Double): Double = round((Math.cos(Math.toRadians(args[0]))*10000))/100
            }
            val tanDeg = object : Function("tan", 1) {
                override fun apply(vararg args: Double): Double = round((Math.tan(Math.toRadians(args[0]))*100))/100
            }

            val expression = ExpressionBuilder(input)
                .functions(sinDeg, cosDeg, tanDeg)
                .build()

            val result = expression.evaluate()

            // Format result: Remove decimal point if it's a whole number
            display.text = if (result % 1 == 0.0) {
                result.toLong().toString()
            } else {
                result.toString()
            }
        } catch (e: Exception) {
            display.text = "Error"
        }
    }
}
