package com.example.pinview

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalComposeUiApi
@Preview(showBackground = false)
@Composable
fun DashCenterPreview() {
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
            filled = { char: Char, _: Int ->
                FilledDash(char)
            }) {
        }
    }
}

@Composable
private fun EmptyDash() {
    Block(content = {
        Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(top = 6.dp))
    })
}

@Composable
private fun FilledDash(char: Char) {
    val text by remember { mutableStateOf(char) }
    Block(content = {
        Text(text = text.toString(), fontSize = 36.sp, textAlign = TextAlign.Center)
    })
}

@Composable
private fun Block(content: @Composable () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .size(44.dp)
    ) {
        content.invoke()
    }
}