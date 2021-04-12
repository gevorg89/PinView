package com.example.pinview

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            empty = { EmptyDash() },
            filled = { _: Char, _: Int -> FilledDash() }) { pinData ->
        }
    }
}

@Composable
private fun EmptyDash() {
    Block(content = { }, color = Color.Gray)
}

@Composable
private fun FilledDash() {
    Block(content = {
        Text(text = "*", fontSize = 24.sp)
    }, color = Color.Blue)
}

@Composable
private fun Block(content: @Composable () -> Unit, color: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .border(color = color, width = 2.dp, shape = RoundedCornerShape(8.dp))
            .size(44.dp)
    ) {
        content.invoke()
    }
}