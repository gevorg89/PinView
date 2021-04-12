package com.example.pinview

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalComposeUiApi
@Preview(showBackground = false)
@Composable
fun CirclePreview() {
    var pinDataState by remember { mutableStateOf(PinData(text = "")) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = if (pinDataState.text.isNotEmpty()) {
                "Your code:${pinDataState.text}, complete:${pinDataState.complete}"
            } else {
                "complete:${pinDataState.complete}"
            }, fontSize = 17.sp
        )
        PinView(
            modifier = Modifier.padding(top = 16.dp),
            count = 5,
            empty = { EmptyCircle() },
            filled = { _: Char, _: Int -> FilledCircle() }) { pinData ->
            pinDataState = pinData
        }
    }
}


@Composable
private fun EmptyCircle() {
    Circle(content = { }, color = Color.Gray)
}

@Composable
private fun FilledCircle() {
    var animationValue by remember { mutableStateOf(1f) }
    val animationState by animateFloatAsState(
        targetValue = animationValue,
        animationSpec = tween(durationMillis = 100),
        finishedListener = {
            animationValue = 1f
        }
    )
    Circle(
        content = {},
        color = Color.Cyan,
        modifier = Modifier.scale(animationState)
    )
    DisposableEffect(Unit) {
        animationValue = 1.5f
        onDispose {

        }
    }
}

@Composable
private fun Circle(content: @Composable () -> Unit, color: Color, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(32.dp)
    ) {
        Column(
            modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color)
        ) {
            content.invoke()
        }
    }
}