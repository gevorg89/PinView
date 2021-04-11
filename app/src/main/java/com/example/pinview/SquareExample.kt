package com.example.pinview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Preview(showBackground = false)
@Composable
fun SquarePreview() {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
    ) {
        PinView(
            count = 4,
            empty = { EmptySquare() },
            filled = { FilledSquare() }) { pinData ->

        }
    }
}

@Composable
private fun EmptySquare() {
    Square(content = { }, color = Color.Gray)
}

@Composable
private fun FilledSquare() {
    Square(content = {
        Text(text = "*")
    }, color = Color.Cyan)
}

@Composable
private fun Square(content: @Composable () -> Unit, color: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .background(color)
            .size(44.dp)
    ) {
        content.invoke()
    }
}