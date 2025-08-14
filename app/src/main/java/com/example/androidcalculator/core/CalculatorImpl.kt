package com.example.androidcalculator.core

import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class CalculatorImpl : Calculator {
    override fun evaluateBasic(a: Double, b: Double, op: String): Double {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            "^" -> a.pow(b)
            else -> throw IllegalArgumentException("Unknown operator")
        }
    }

    override fun evaluateFunction(value: Double, func: String): Double {
        return when (func.lowercase()) {
            "sin" -> sin(value)
            "cos" -> cos(value)
            "tan" -> tan(value)
            "log" -> log10(value)
            "ln"  -> ln(value)
            "sqrt" -> sqrt(value)
            else -> throw IllegalArgumentException("Unknown function")
        }
    }
}