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
fun MergedPreview() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
    ) {
        PinView(
            count = 4,
            empty = { position: Int ->
                EmptyDash(position)
            },
            filled = { _: Char, position: Int ->
                FilledDash(position)
            }) { }
    }
}

@Composable
private fun EmptyDash(position: Int) {
    Block(content = { }, color = Color.Gray, position = position)
}

@Composable
private fun FilledDash(position: Int) {
    Block(
        content = { Text(text = "*", fontSize = 24.sp) },
        color = Color.Blue,
        position = position
    )
}

@Composable
private fun Block(
    content: @Composable () -> Unit,
    color: Color,
    position: Int
) {
    val radius = 8.dp
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                color = color,
                width = 2.dp,
                shape = when (position) {
                    0 -> RoundedCornerShape(topStart = radius, bottomStart = radius)
                    3 -> RoundedCornerShape(topEnd = radius, bottomEnd = radius)
                    else -> RoundedCornerShape(0.dp)
                }
            )
            .size(44.dp)
    ) {
        content.invoke()
    }
}