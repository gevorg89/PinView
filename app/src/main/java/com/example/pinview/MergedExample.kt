package com.example.pinview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val count = 5
private val radius = 8.dp
private val color = Color.Gray

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
            modifier = Modifier.border(
                color = color,
                width = 2.dp,
                shape = RoundedCornerShape(radius)
            ),
            keyboardType = KeyboardType.Ascii,
            count = count,
            empty = { position: Int ->
                EmptyDash(position)
            },
            filled = { char: Char, position: Int ->
                FilledDash(char, position)
            }) { }
    }
}

@Composable
private fun EmptyDash(position: Int) {
    Block(content = { }, position = position)
}

@Composable
private fun FilledDash(char: Char, position: Int) {
    val text by remember { mutableStateOf(char) }
    Block(
        content = { Text(text = text.toString(), fontSize = 28.sp) },
        position = position
    )
}

@Composable
private fun Block(
    content: @Composable () -> Unit,
    position: Int
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(if (position > 0) BorderStroke(2.dp, color) else null)
            .size(44.dp)
    ) {
        content.invoke()
    }
}

fun Modifier.border(borderStroke: BorderStroke?) =
    drawBehind {
        borderStroke?.let {
            drawStartBorder(stroke = borderStroke)
        }
    }

private fun DrawScope.drawStartBorder(
    stroke: BorderStroke
) {
    val strokeWidthPx = stroke.width.toPx()
    if (strokeWidthPx == 0f) return
    drawPath(
        Path().apply {
            moveTo(0f, 0f)
            lineTo(strokeWidthPx, 0f)
            val height = size.height
            lineTo(strokeWidthPx, height)
            lineTo(0f, height)
            close()
        },
        brush = stroke.brush
    )
}