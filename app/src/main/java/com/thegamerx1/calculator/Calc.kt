package com.thegamerx1.calculator

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode


enum class Action {
    DIVIDE, MULTIPLY, SUM, SUBTRACT
}

fun ActionToString(action: Action?): String {
    return when (action) {
        Action.DIVIDE -> "÷"
        Action.MULTIPLY -> "×"
        Action.SUM -> "+"
        Action.SUBTRACT -> "-"
        null -> ""
    }
}

val MathContext = MathContext(10,RoundingMode.UP);

data class Calc(
    var result: BigDecimal? = null,
    var action: Action? = null,
    var first: BigDecimal? = null,
    var second: BigDecimal? = null,
    var isDotting: Boolean = false,
) {
    fun action(act: Action): Calc {
        updateResult()
        return this.copy(action = act)
    }


    fun calcResult(): Calc {
        if (first == null || second == null) return this
        return Calc(
            result = when (action) {
                Action.DIVIDE -> first!!.divide(second!!,MathContext)
                Action.MULTIPLY -> first!! * second!!
                Action.SUM -> first!! + second!!
                Action.SUBTRACT -> first!! - second!!
                null -> null
            }
        )
    }

    fun dot(): Calc {
        updateResult()
        return this.copy(isDotting = true)
    }

    fun delDigit(): Calc {
        updateResult()
        val changing = (if (action == null) first else second).toString().dropLast(1).toBigDecimalOrNull()
        if (changing == null && action != null) {
            return this.copy(action = null, second = null)
        }

        return if (action == null) {
            this.copy(first = changing)
        } else {
            this.copy(second = changing)
        }
    }

    fun addDigit(number: Int): Calc {
        updateResult()
        val changing = _addDigit(if (action == null) first else second, number)

        return if (action == null) {
            this.copy(
                first = changing
            )
        } else {
            this.copy(
                second = changing
            )
        }
    }

    private fun updateResult() {
        if (result != null) {
            first = result
            result = null
        }
    }

    private fun _addDigit(original: BigDecimal?, add: Int): BigDecimal {
        return if (original == null) {
            BigDecimal(add)
        } else {
            original * BigDecimal(10) + BigDecimal(add)
        }
    }

}
