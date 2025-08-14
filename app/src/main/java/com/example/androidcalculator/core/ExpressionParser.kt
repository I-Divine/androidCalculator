package com.example.androidcalculator.core

interface ExpressionParser {
    fun evaluateExpression(expr: String): Double;
}