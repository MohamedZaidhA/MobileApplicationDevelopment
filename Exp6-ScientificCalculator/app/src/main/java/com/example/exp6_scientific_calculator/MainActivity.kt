package com.example.exp6_scientific_calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.div
import kotlin.mod
import kotlin.text.clear
import java.lang.Exception

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
        val display : TextView = findViewById(R.id.display)

        val num1 : Button = findViewById(R.id.num1)
        val num2 : Button = findViewById(R.id.num2)
        val num3 : Button = findViewById(R.id.num3)
        val num4 : Button = findViewById(R.id.num4)
        val num5 : Button = findViewById(R.id.num5)
        val num6 : Button = findViewById(R.id.num6)
        val num7 : Button = findViewById(R.id.num7)
        val num8 : Button = findViewById(R.id.num8)
        val num9 : Button = findViewById(R.id.num9)
        val num0 : Button = findViewById(R.id.num0)
        val dot : Button = findViewById(R.id.dot)
        val add : Button = findViewById(R.id.add)
        val sub : Button = findViewById(R.id.sub)
        val mul : Button = findViewById(R.id.mul)
        val div : Button = findViewById(R.id.div)
        val sin : Button = findViewById(R.id.sin)
        val cos : Button = findViewById(R.id.cos)
        val tan : Button = findViewById(R.id.tan)
        val log : Button = findViewById(R.id.log)
        val sqrt : Button = findViewById(R.id.sqrt)
        val btnEquals : Button = findViewById(R.id.equal)
        val clear : Button = findViewById(R.id.clear)


        num1.setOnClickListener {
            display.text = (display.text.toString() + "1")
        }
        num2.setOnClickListener {
            display.text = (display.text.toString() + "2")
        }
        num3.setOnClickListener {
            display.text = (display.text.toString() + "3")
        }
        num4.setOnClickListener {
            display.text = (display.text.toString() + "4")
        }
        num5.setOnClickListener {
            display.text = (display.text.toString() + "5")
        }
        num6.setOnClickListener {
            display.text = (display.text.toString() + "6")
        }
        num7.setOnClickListener {
            display.text = (display.text.toString() + "7")
        }
        num8.setOnClickListener {
            display.text = (display.text.toString() + "8")
        }
        num9.setOnClickListener {
            display.text = (display.text.toString() + "9")
        }
        num0.setOnClickListener {
            display.text = (display.text.toString() + "0")
        }
        dot.setOnClickListener {
            display.text = (display.text.toString() + ".")
        }
        add.setOnClickListener {
            display.text = (display.text.toString() + "+")
        }
        sub.setOnClickListener {
            display.text = (display.text.toString() + "-")
        }
        mul.setOnClickListener {
            display.text = (display.text.toString() + "*")
        }
        div.setOnClickListener {
            display.text = (display.text.toString() + "/")
        }
        sin.setOnClickListener {
            display.text = (display.text.toString() + "sin")
        }
        cos.setOnClickListener {
            display.text = (display.text.toString() + "cos")
        }
        tan.setOnClickListener {
            display.text = (display.text.toString() + "tan")
        }
        log.setOnClickListener {
            display.text = (display.text.toString() + "log")
        }
        sqrt.setOnClickListener {
            display.text = (display.text.toString() + "√")
        }
        clear.setOnClickListener {
            display.text = ""
        }
        fun evaluateCustom(expression: String): Double {
            return object : Any() {
                var pos = -1
                var ch = 0

                fun nextChar() {
                    ch = if (++pos < expression.length) expression[pos].toInt() else -1
                }

                fun eat(charToEat: Int): Boolean {
                    while (ch == ' '.toInt()) nextChar()
                    if (ch == charToEat) {
                        nextChar()
                        return true
                    }
                    return false
                }

                fun parse(): Double {
                    nextChar()
                    val x = parseExpression()
                    if (pos < expression.length) throw RuntimeException("Unexpected: " + ch.toChar())
                    return x
                }

                // Handles Addition and Subtraction
                fun parseExpression(): Double {
                    var x = parseTerm()
                    while (true) {
                        if (eat('+'.toInt())) x += parseTerm()
                        else if (eat('-'.toInt())) x -= parseTerm()
                        else return x
                    }
                }

                // Handles Multiplication and Division
                fun parseTerm(): Double {
                    var x = parseFactor()
                    while (true) {
                        if (eat('*'.toInt())) x *= parseFactor()
                        else if (eat('/'.toInt())) x /= parseFactor()
                        else return x
                    }
                }

                // Handles Numbers and Functions (sin, cos, sqrt)
                fun parseFactor(): Double {
                    if (eat('+'.toInt())) return parseFactor()
                    if (eat('-'.toInt())) return -parseFactor()

                    var x: Double
                    val startPos = pos
                    if (eat('('.toInt())) {
                        x = parseExpression()
                        eat(')'.toInt())
                    } else if (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) {
                        while (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) nextChar()
                        x = expression.substring(startPos, pos).toDouble()
                    } else if (ch >= 'a'.toInt() && ch <= 'z'.toInt()) {
                        while (ch >= 'a'.toInt() && ch <= 'z'.toInt()) nextChar()
                        val func = expression.substring(startPos, pos)
                        x = parseFactor()
                        x = when (func) {
                            "sqrt" -> Math.sqrt(x)
                            "sin" -> Math.sin(Math.toRadians(x)) // Automatically converts to Degrees!
                            "cos" -> Math.cos(Math.toRadians(x))
                            "tan" -> Math.tan(Math.toRadians(x))
                            "log" -> Math.log10(x)
                            else -> throw RuntimeException("Unknown function: $func")
                        }
                    } else {
                        throw RuntimeException("Unexpected: " + ch.toChar())
                    }
                    return x
                }
            }.parse()
        }
        btnEquals.setOnClickListener {
            try {
                val rawInput = display.text.toString()
                // Clean up symbols to match our parser
                val cleanInput = rawInput.replace("√", "sqrt")

                val result = evaluateCustom(cleanInput)
                display.text = if (result % 1 == 0.0) result.toLong().toString() else result.toString()
            } catch (e: Exception) {
                display.text = "Error"
            }
        }
        }
    }
