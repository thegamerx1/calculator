package com.thegamerx1.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode


enum class Action {
    DIVIDE, MULTIPLY, SUM, SUBTRACT
}

fun actionToString(action: Action?): String {
    return when (action) {
        Action.DIVIDE -> "÷"
        Action.MULTIPLY -> "×"
        Action.SUM -> "+"
        Action.SUBTRACT -> "-"
        null -> ""
    }
}

val MathContext = MathContext(10, RoundingMode.UP);

class Calc {
    var result: BigDecimal? by mutableStateOf(null)
    var action: Action? by mutableStateOf(null)
    var first: BigDecimal? by mutableStateOf(null)
    var second: BigDecimal? by mutableStateOf(null)
    var isDotting: Boolean by mutableStateOf(false)

    fun reset() {
        result = null
        action = null
        first = null
        second = null
        isDotting = false
    }


    fun action(act: Action) {
        updateResult()
        action = act
    }


    fun calcResult() {
        if (first == null || second == null) return
        val _result = when (action) {
            Action.DIVIDE -> first!!.divide(second!!, MathContext)
            Action.MULTIPLY -> first!! * second!!
            Action.SUM -> first!! + second!!
            Action.SUBTRACT -> first!! - second!!
            null -> null
        }
        reset()
        result = _result
    }

    fun dot() {
        updateResult()
        isDotting = true
    }

    fun delDigit() {
        updateResult()
        var changing = if (action == null) first else second
        if (changing == null && action != null) {
            action = null
            second = null
            return
        } else {
            changing = changing.toString().dropLast(1).toBigDecimalOrNull()
        }

        if (action == null) {
            first = changing
        } else {
            second = changing
        }
    }

    fun pressDigit(number: Int) {
        updateResult()
        val changing = addDigit(if (action == null) first else second, number)

        if (action == null) {
            first = changing
        } else {
            second = changing
        }
    }

    private fun updateResult() {
        if (result != null) {
            first = result
            result = null
        }
    }

    private fun addDigit(original: BigDecimal?, add: Int): BigDecimal {
        return if (original == null) {
            BigDecimal(add)
        } else {
            original * BigDecimal(10) + BigDecimal(add)
        }
    }

}
