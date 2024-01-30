package com.example.assignment3_bigger_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.BoolRes
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var resultTv: TextView
    private lateinit var buttonC: MaterialButton
    private lateinit var buttonNegate: MaterialButton
    private lateinit var buttonModulus: MaterialButton
    private lateinit var buttonDivide: MaterialButton
    private lateinit var buttonMultiply: MaterialButton
    private lateinit var buttonPlus: MaterialButton
    private lateinit var buttonMinus: MaterialButton
    private lateinit var buttonEquals: MaterialButton
    private lateinit var button0: MaterialButton
    private lateinit var button1: MaterialButton
    private lateinit var button2: MaterialButton
    private lateinit var button3: MaterialButton
    private lateinit var button4: MaterialButton
    private lateinit var button5: MaterialButton
    private lateinit var button6: MaterialButton
    private lateinit var button7: MaterialButton
    private lateinit var button8: MaterialButton
    private lateinit var button9: MaterialButton
    private lateinit var buttonAC: MaterialButton
    private lateinit var buttonDot: MaterialButton

    private var currentInput = StringBuilder() // NOTE -> used chatGPT to decide how to keep track of input (via StringBuilder)
    private var currentOperator: String? = null

    private var firstOperand: Double? = null
    private var secondOperand: Double? = null
    private var onFirstOperand = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize views
        resultTv = findViewById(R.id.result_tv)
        buttonC = findViewById(R.id.button_c)
        buttonNegate = findViewById(R.id.button_negate)
        buttonModulus = findViewById(R.id.button_mod)
        buttonDivide = findViewById(R.id.button_divide)
        buttonMultiply = findViewById(R.id.button_multiply)
        buttonPlus = findViewById(R.id.button_plus)
        buttonMinus = findViewById(R.id.button_minus)
        buttonEquals = findViewById(R.id.button_equals)
        button0 = findViewById(R.id.button_0)
        button1 = findViewById(R.id.button_1)
        button2 = findViewById(R.id.button_2)
        button3 = findViewById(R.id.button_3)
        button4 = findViewById(R.id.button_4)
        button5 = findViewById(R.id.button_5)
        button6 = findViewById(R.id.button_6)
        button7 = findViewById(R.id.button_7)
        button8 = findViewById(R.id.button_8)
        button9 = findViewById(R.id.button_9)
        buttonAC = findViewById(R.id.button_ac)
        buttonDot = findViewById(R.id.button_dot)


        // set onClick listeners for each view
        button0.setOnClickListener { appendInput("0") }
        button1.setOnClickListener { appendInput("1") }
        button2.setOnClickListener { appendInput("2") }
        button3.setOnClickListener { appendInput("3") }
        button4.setOnClickListener { appendInput("4") }
        button5.setOnClickListener { appendInput("5") }
        button6.setOnClickListener { appendInput("6") }
        button7.setOnClickListener { appendInput("7") }
        button8.setOnClickListener { appendInput("8") }
        button9.setOnClickListener { appendInput("9") }
        buttonPlus.setOnClickListener { handleOperator("+") }
        buttonMinus.setOnClickListener { handleOperator("-") }
        buttonMultiply.setOnClickListener { handleOperator("*") }
        buttonDivide.setOnClickListener { handleOperator("/") }
        buttonEquals.setOnClickListener { calculateResult() }
        buttonC.setOnClickListener { clearLastInput() }
        buttonAC.setOnClickListener { clearAll() }
        buttonDot.setOnClickListener { appendInput(".") }

    }

    private fun appendInput(digit: String) {
        // handle case for double decimals
        if (digit == "." && currentInput.isNotEmpty() && currentInput.last() == '.') {
            return
        }
        currentInput.append(digit)
        updateDisplay()

        // update operands accordingly
        if (onFirstOperand) {
            firstOperand = currentInput.toString().toDouble()
        } else {
            secondOperand = currentInput.toString().toDouble()
        }
    }

    private fun handleOperator(operator: String) {
        // check if current operator is already set (perform current preceding operation... regardless of PEMDAS)
        if (currentOperator != null) {
            // If there is an existing operator, perform the calculation
            calculateResult()
        }
        // otherwise, we are setting this operator for the first time (must swap operands)
        else {
            onFirstOperand = !onFirstOperand
            currentInput.clear()
            updateDisplay()
        }

        currentOperator = operator
        updateDisplay()
    }

    private fun calculateResult() {
        if (currentOperator !== null) {
            // Perform the calculation based on the current input and operator
            val result = performCalculation()

            // update currentInput with result of calculation
            currentInput.clear()
            if (result > 0) {
                currentInput.append(result.toString())
            }

            // update operands
            if (onFirstOperand) {
                firstOperand = currentInput.toString().toDouble()
            } else {
                secondOperand = currentInput.toString().toDouble()
            }

            // clear current operator
            currentOperator = null
            updateDisplay()
        }
    }

    private fun performCalculation(): Double {
        // Implement the calculation based on the current input and operator
        if (firstOperand == null || secondOperand == null) {
            return 0.0
        }
        return when (currentOperator) {
                "+" -> firstOperand!! + secondOperand!!
                "-" -> firstOperand!! - secondOperand!!
                "*" -> firstOperand!! * secondOperand!!
//                "/" -> firstOperand!! + secondOperand!!
            else -> 0.0
        }
    }

    private fun clearLastInput() {
        if (currentInput.isEmpty()) {
            return
        }
        // Clear the last entered digit or operator
        currentInput.deleteCharAt(currentInput.length - 1)
        // if not input, clear all
        if (currentInput.isEmpty()) {
            clearAll()
        }
        updateDisplay()
    }

    private fun clearAll() {
        // Clear all input and reset the calculator
        currentInput.clear()
        currentOperator = null
        firstOperand = null
        secondOperand = null
        onFirstOperand = true
        updateDisplay()
    }

    private fun updateDisplay() {
        // Update the inputTextView and resultTextView with the current input and result
        resultTv.text = if (currentInput.isNotEmpty()) {
            currentInput.toString()
        } else {
            "0"
        }
    }
}