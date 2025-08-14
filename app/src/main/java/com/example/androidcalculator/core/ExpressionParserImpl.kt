package com.example.androidcalculator.core

import android.util.Log
import android.widget.Toast
import com.example.androidcalculator.MainActivity

class ExpressionParserImpl : ExpressionParser{


    private val calculator:Calculator = CalculatorImpl()

    override fun evaluateExpression(expr: String): Double {
        val trimmed = expr.replace("\\s".toRegex(), "")

        // Handle functions like sin, cos, etc.
        val functionMatch = Regex("(sin|cos|tan|log|ln|sqrt)\\(([^)]+)\\)", RegexOption.IGNORE_CASE)
        val funcResult = functionMatch.find(trimmed)
        if (funcResult != null) {
            val funcName = funcResult.groupValues[1]
            val value = evaluateExpression(funcResult.groupValues[2])
            Log.d("function name","funcName : "+funcName)
            return calculator.evaluateFunction(value, funcName)
        }

        // Handle parentheses
        val parenRegex = Regex("\\(([^()]+)\\)")
        var innerExpr = trimmed
        while (parenRegex.containsMatchIn(innerExpr)) {
            innerExpr = parenRegex.replace(innerExpr) {
                evaluateExpression(it.groupValues[1]).toString()
            }
        }

        // Handle basic operations (order: ^, /, *, +, -)
        val ops = listOf("^", "/", "*", "+", "-")
        for (op in ops) {
            val regex = when (op) {
                "^" -> Regex("(-?\\d+(\\.\\d+)?)(\\^)(-?\\d+(\\.\\d+)?)")
                "/" -> Regex("(-?\\d+(\\.\\d+)?)(/)(-?\\d+(\\.\\d+)?)")
                "*" -> Regex("(-?\\d+(\\.\\d+)?)(\\*)(-?\\d+(\\.\\d+)?)")
                "+" -> Regex("(-?\\d+(\\.\\d+)?)(\\+)(-?\\d+(\\.\\d+)?)")
                "-" -> Regex("(-?\\d+(\\.\\d+)?)(\\-)(-?\\d+(\\.\\d+)?)")
                else -> continue
            }

            val match = regex.find(innerExpr)
            if (match != null) {
                val a = match.groupValues[1].toDouble()
                val b = match.groupValues[4].toDouble()
                val res = calculator.evaluateBasic(a, b, op)
                return evaluateExpression(innerExpr.replaceFirst(regex, res.toString()))
            }
        }

        // If no operators left, just return number
        return innerExpr.toDouble()
    }
}