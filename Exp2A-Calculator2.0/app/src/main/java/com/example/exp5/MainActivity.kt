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
import java.util.Stack
import kotlin.math.pow


class MainActivity : AppCompatActivity() {
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val display : TextView = findViewById(R.id.display)
        val display2 : TextView = findViewById(R.id.display2)
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
        val add : Button = findViewById(R.id.add)
        val sub : Button = findViewById(R.id.sub)
        val mul : Button = findViewById(R.id.mul)
        val div : Button = findViewById(R.id.div)
        val mod : Button = findViewById(R.id.mod)
        val clear : Button = findViewById(R.id.clear)
        val reset : Button = findViewById(R.id.reset)
        val ans : Button = findViewById(R.id.ans)
        var count : Int = 0
        val sym : String = "+-X/%"

        num1.setOnClickListener {
            display.text = (display.text.toString() + "1")
            count=0
        }
        num2.setOnClickListener {
            display.text = (display.text.toString() + "2")
            count=0
        }
        num3.setOnClickListener {
            display.text = (display.text.toString() + "3")
            count=0
        }
        num4.setOnClickListener {
            display.text = (display.text.toString() + "4")
            count=0
        }
        num5.setOnClickListener {
            display.text = (display.text.toString() + "5")
            count=0
        }
        num6.setOnClickListener {
            display.text = (display.text.toString() + "6")
            count=0
        }
        num7.setOnClickListener {
            display.text = (display.text.toString() + "7")
            count=0
        }
        num8.setOnClickListener {
            display.text = (display.text.toString() + "8")
            count=0
        }
        num9.setOnClickListener {
            display.text = (display.text.toString() + "9")
            count=0
        }
        num0.setOnClickListener {
            display.text = (display.text.toString() + "0")
            count=0
        }
        add.setOnClickListener {
            if (count>0){
                display2.text="operator can't be used"
            }
            else{
            display.text = (display.text.toString() + " + ")
            count=1}
        }
        sub.setOnClickListener {
            if (count>1){
                display2.text="operator can't be used"
            }
            else{
                display.text = (display.text.toString() + " - ")
                count=2}
        }
        mul.setOnClickListener {
            if (count>0){
                display2.text="operator can't be used"
            }
            else{
                display.text = (display.text.toString() + " * ")
                count=1}
        }
        mod.setOnClickListener {
            if (count>0){
                display2.text="operator can't be used"
            }
            else{
                display.text = (display.text.toString() + " ^ ")
                count=1}
        }
        div.setOnClickListener {
            if (count>0){
                display2.text="operator can't be used"
            }
            else{
                display.text = (display.text.toString() + " / ")
                count=1}
        }
        clear.setOnClickListener {
            if (display.text.last() in "+-X/^"){
                count=0
                display2.text=""
            }
            display.text = display.text.toString().dropLast(1)
        }
        reset.setOnClickListener {
            display.text = ""
            display2.text= ""
            count=0
        }
        fun infixToPostfix(expression: String): String {
            val precedence = mapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2, '^' to 3)
            val operatorStack = Stack<Char>()
            val output = StringBuilder()

            var i = 0
            while (i < expression.length) {
                val char = expression[i]

                when {
                    // Handle multi-digit numbers/decimals
                    char.isDigit() || char == '.' -> {
                        while (i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                            output.append(expression[i++])
                        }
                        output.append(" ") // Add space after number
                        continue
                    }
                    char == '(' -> operatorStack.push(char)
                    char == ')' -> {
                        while (operatorStack.isNotEmpty() && operatorStack.peek() != '(') {
                            output.append(operatorStack.pop()).append(" ")
                        }
                        if (operatorStack.isNotEmpty()) operatorStack.pop()
                    }
                    char in precedence -> {
                        while (operatorStack.isNotEmpty() && operatorStack.peek() != '(') {
                            val top = operatorStack.peek()
                            val currentPrec = precedence[char] ?: 0
                            val topPrec = precedence[top] ?: 0

                            // '^' is right-associative, others are left-associative
                            if ((char != '^' && currentPrec <= topPrec) || (char == '^' && currentPrec < topPrec)) {
                                output.append(operatorStack.pop()).append(" ")
                            } else break
                        }
                        operatorStack.push(char)
                    }
                }
                i++
            }

            while (operatorStack.isNotEmpty()) {
                output.append(operatorStack.pop()).append(" ")
            }
            return output.toString().trim()
        }

        fun evaluatePostfix(expression: String): Double {
            val stack = mutableListOf<Double>()
            // Split the expression into tokens using whitespace as a delimiter
            val tokens = expression.split(" ")

            for (token in tokens) {
                when {
                    // Check if the token is a number
                    token.toDoubleOrNull() != null -> {
                        stack.add(token.toDouble())
                    }
                    // Check if the token is an operator
                    token in setOf("+", "-", "*", "/", "^") -> {
                        if (stack.size < 2) {
                            throw IllegalArgumentException("Invalid postfix expression: not enough operands for operator $token")
                        }
                        // Pop the top two operands (order matters!)
                        val op2 = stack.removeAt(stack.size - 1)
                        val op1 = stack.removeAt(stack.size - 1)

                        // Perform the operation and push the result back
                        val result = when (token) {
                            "+" -> op1 + op2
                            "-" -> op1 - op2
                            "*" -> op1 * op2
                            "/" -> {
                                if (op2 == 0.0) throw ArithmeticException("Division by zero")
                                op1 / op2
                            }
                            "^" -> op1.pow(op2)
                            else -> throw IllegalArgumentException("Unknown operator: $token")
                        }
                        stack.add(result)
                    }
                    else -> {
                        // Handle invalid tokens
                        throw IllegalArgumentException("Invalid token: $token")
                    }
                }
            }

            // The final result should be the only item left in the stack
            if (stack.size != 1) {
                throw IllegalArgumentException("Invalid postfix expression: too many operands left")
            }

            return stack.removeAt(stack.size - 1)
        }
        ans .setOnClickListener {
            display.text = evaluatePostfix(infixToPostfix(display.text.toString())).toString()
        }


    }
}