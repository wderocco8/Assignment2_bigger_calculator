package com.example.assignment3_bigger_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.BoolRes
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var longResultTv: TextView
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


    private var canAddOperation = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize views
        longResultTv = findViewById(R.id.long_result_tv)
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
        button0.setOnClickListener { numberAction(it) }
        button1.setOnClickListener { numberAction(it) }
        button2.setOnClickListener { numberAction(it) }
        button3.setOnClickListener { numberAction(it) }
        button4.setOnClickListener { numberAction(it) }
        button5.setOnClickListener { numberAction(it) }
        button6.setOnClickListener { numberAction(it) }
        button7.setOnClickListener { numberAction(it) }
        button8.setOnClickListener { numberAction(it) }
        button9.setOnClickListener { numberAction(it) }
        buttonDot.setOnClickListener { numberAction(it) }
        buttonPlus.setOnClickListener { handleOperation(it) }
        buttonMinus.setOnClickListener { handleOperation(it) }
        buttonMultiply.setOnClickListener { handleOperation(it) }
        buttonDivide.setOnClickListener { handleOperation(it) }
        buttonModulus.setOnClickListener { handleOperation(it) }
        buttonEquals.setOnClickListener { equals(it) }
        buttonC.setOnClickListener { clearLastInput() }
        buttonAC.setOnClickListener { clearAll() }

    }

    private fun numberAction(view: View) {
        // confirm that view we are looking at is a button input
        if (view is Button) {
            // handle decimal edge case
            if (view.text == ".") {
                if (canAddDecimal) {
                    longResultTv.append(view.text)
                    canAddDecimal = false
                }
            }
            // handle division/mod by zero
            else if (longResultTv.text.isNotEmpty() && (longResultTv.text.last() == '/' || longResultTv.text.last() == '%') && view.text == "0") {
                Toast.makeText(this, "Cannot divide by 0", Toast.LENGTH_SHORT).show()
                return
            }
            // handle
            else {
                longResultTv.append(view.text)
            }
            canAddOperation = true
        }
    }

    private fun handleOperation(view: View) {
        if (view is Button && canAddOperation) {
            // add operator to top display (cannot add additional operation right now)
            longResultTv.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }


    // NOTE -> the following functions (calculateResults, timesDivisionCalc, calcTimesDiv, digitsOperators) were based off of this tutorial https://www.youtube.com/watch?v=2hSHgungOKI
    private fun equals(view: View) {
        resultTv.text = calculateResults()
    }

    private fun calculateResults(): String {
        val digitsList = digitsOperators()
        // edge case -> no calculations
        if (digitsList.isEmpty()) return ""


        val timesDivison = timesDivisionCalc(digitsList)
        // if no data from timesDivision calc, return ""
        if (timesDivison.isEmpty()) return ""

        val result = addSubtractCalc(timesDivison)
        return result.toString()
    }

    // helper function to perform remaining add/subtract operators (last component of PEMDAS)
    private fun addSubtractCalc(passedList: MutableList<Any>): Float
    {
        var result = passedList[0] as Float

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }

        return result
    }


    // helper function to find important operators first
    private fun timesDivisionCalc(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while (list.contains('*') || list.contains('/') || list.contains('%'))
        {
            // does each multiplication, divison operation one-by-one
            list = calcTimesDiv(list)
        }
        return list
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any>
    {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when(operator)
                {
                    '*' ->
                    {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' ->
                    {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    '%' ->
                    {
                        newList.add(prevDigit % nextDigit)
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }

            if(i > restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }


    private fun digitsOperators(): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (ch in longResultTv.text) {
            // digit case
            if (ch.isDigit() || ch == '.') {
                currentDigit += ch
            }
            // operator case
            else {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(ch) // add operator to list
            }
        }

        // add final digit to list (if applicable)
        if (currentDigit.isNotEmpty()) {
            list.add(currentDigit.toFloat())
        }

        return list
    }


    private fun clearLastInput() {
        val length = longResultTv.length()
        if (length > 0) {
            longResultTv.text = longResultTv.text.subSequence(0, length - 1)
        }
    }

    private fun clearAll() {
        // Clear all input and reset the calculator
        longResultTv.text = ""
        resultTv.text = "0"
        canAddDecimal = true
    }

}