package com.example.assignment3_bigger_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
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

    private lateinit var result: Number

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

    }


}