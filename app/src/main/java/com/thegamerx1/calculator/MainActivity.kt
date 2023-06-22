package com.thegamerx1.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thegamerx1.calculator.ui.theme.CalculatorTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator()
                }
            }
        }
    }

    @Composable
    @Preview
    private fun Calculator() {
        var calculator by remember { mutableStateOf(Calc()) }

        @Composable
        fun btn(onClick: () -> Calc, text: String, modifier: Modifier) {
            return Button(
                onClick = {calculator = onClick()},
                shape = CutCornerShape(0),
                modifier = modifier
                    .fillMaxHeight()
                    .padding(1.dp)
            ) {
                Text(text = text)
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)) {
            Column(modifier = Modifier.weight(2F)) {
                Text(calculator.first?.toString() + " " + calculator.action.toString() + " " + calculator.second?.toString())
                Text(text = calculator.result.toString(), fontSize = 32.sp)
            }
            Row(modifier = Modifier.weight(1F)) {
                btn({ Calc() }, "AC", Modifier.weight(1f))
                btn({ calculator.delDigit() }, "Del", Modifier.weight(2f))
                btn({ calculator.action(Action.SUM) }, "+", Modifier.weight(1f))
            }
            Row(modifier = Modifier.weight(1F)) {
                listOf(1, 2, 3).forEach {
                    btn(
                        { calculator.addDigit(it) },
                        it.toString(),
                        Modifier.weight(1f)
                    )
                }
                btn({ calculator.action(Action.SUBTRACT) }, "-", Modifier.weight(1f))
            }
            Row(modifier = Modifier.weight(1F)) {
                listOf(4, 5, 6).forEach {
                    btn(
                        { calculator.addDigit(it) },
                        it.toString(),
                        Modifier.weight(1f)
                    )
                }
                btn({ calculator.action(Action.DIVIDE) }, "/", Modifier.weight(1f))
            }
            Row(modifier = Modifier.weight(1F)) {
                listOf(7, 8, 9).forEach {
                    btn(
                        { calculator.addDigit(it) },
                        it.toString(),
                        Modifier.weight(1f)
                    )
                }
                btn({ calculator.action(Action.MULTIPLY) }, "*", Modifier.weight(1f))
            }
            Row(modifier = Modifier.weight(1F)) {
                btn({ calculator.addDigit(0) }, "0", Modifier.weight(2f))
                btn({ calculator.dot() }, ".", Modifier.weight(1f))
                btn({ calculator.calcResult() }, "=", Modifier.weight(1f))
            }
        }
    }


}