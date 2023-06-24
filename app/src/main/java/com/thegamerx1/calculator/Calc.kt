package com.thegamerx1.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow


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

val Rounding = RoundingMode.HALF_UP

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
            Action.DIVIDE -> first!!.divide(second!!, 8, Rounding).stripTrailingZeros()
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
        var changing = if (action == null) first else second
        changing = if (isDotting) {
            addDecimal(changing ?: BigDecimal(0), BigDecimal(number))
        } else {
            addDigit(changing, number)
        }


        if (action == null) {
            first = changing
        } else {
            second = changing
        }
    }

    private fun addDecimal(number: BigDecimal, decimal: BigDecimal): BigDecimal {
        val scale = (number.scale() + 1).coerceAtMost(10)
        return decimal.divide(BigDecimal(10.0.pow(scale).toString()), scale, Rounding).add(number)
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
