package com.thegamerx1.calculator

import java.math.BigDecimal


enum class Action {
    DIVIDE, MULTIPLY, SUM, SUBTRACT
}


data class Calc(
    val result: BigDecimal? = null,
    val action: Action? = null,
    val first: BigDecimal? = null,
    val second: BigDecimal? = null,
    val isDotting: Boolean = false,
) {
    fun action(act: Action): Calc {
        return this.copy(action = act)
    }


    fun calcResult(): Calc {
        if (first == null || second == null) return this
        return Calc(
            result = when (action) {
                Action.DIVIDE -> first!! / second!!
                Action.MULTIPLY -> first!! * second!!
                Action.SUM -> first!! + second!!
                Action.SUBTRACT -> first!! - second!!
                null -> null
            }
        )
    }

    fun dot(): Calc {
        return this.copy(isDotting = true)
    }

    fun delDigit(): Calc {
        var changing = if (action == null) first else second
        changing.toString().dropLast(1).toBigDecimalOrNull()
        if (changing == null && action != null) {
            return this.copy(action = null, second = null)
        }

        if (action != null) {
            return this.copy(first = changing)
        } else {
            return this.copy(second = changing)
        }
    }

    fun addDigit(number: Int): Calc {
        var changing = if (action == null) first else second
        changing = if (changing != null) {
            changing!! * BigDecimal(10) + BigDecimal(number)
        } else {
            BigDecimal(number)
        }

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
}
