package com.example.androidcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidcalculator.core.ExpressionParser
import com.example.androidcalculator.core.ExpressionParserImpl

class MainActivity : AppCompatActivity() {
    private lateinit var tvDisplay: TextView
    private val expressionParser:ExpressionParser = ExpressionParserImpl()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }



//        Input Output Logic

        tvDisplay = findViewById(R.id.tvDisplay)


        val buttonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnDot, R.id.btnEquals, R.id.btnClear, R.id.btnDelete,
            R.id.btnPower, R.id.btnSqrt, R.id.btnSin, R.id.btnCos, R.id.btnTan, R.id.btnLog
        )

        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener { button ->
                val text = (button as Button).text.toString()

                when (text) {
                    "Clear" -> tvDisplay.text = "0"
                    "Del" -> {
                        val current = tvDisplay.text.toString()
                        if (current.isNotEmpty()) {
                            tvDisplay.text = current.dropLast(1)
                        }
                        if (tvDisplay.text.isEmpty()) {
                            tvDisplay.text = "0"
                        }
                    }
                    "=" -> {
                        try {
                            val expression = tvDisplay.text.toString()
                            val result = expressionParser.evaluateExpression(expression)
                            tvDisplay.text = result.toString()
                        } catch (e: Exception) {
                            tvDisplay.text = "Error"
                        }
                    }
                    else -> {
                        if (tvDisplay.text.toString() == "0") {
                            tvDisplay.text = text
                        } else {
                            tvDisplay.append(text)
                        }
                    }

                }
            }
        }
    }
}