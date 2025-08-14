package com.example.androidcalculator.core

interface Calculator {
    fun evaluateBasic(a: Double, b: Double, op: String): Double;
    fun evaluateFunction(value: Double, func: String): Double;
}