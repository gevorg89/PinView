package com.example.pinview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Composable
fun PinView2(count: Int) {
    var textState by remember {
        mutableStateOf(
            TextFieldValue(
                text = ""
            )
        )
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = FocusRequester()
    var previewText = textState.text
    Row(Modifier.padding(top = 0.dp)) {
        repeat(textState.text.length) { position ->
            Filled() {
                //textState = textState.copy(selection = TextRange(position))
            }
        }
        repeat(count - textState.text.length) { position ->
            val calcPosition = textState.text.length + position
            Empty {
                //textState = textState.copy(selection = TextRange(calcPosition))
            }
        }
    }
    Row(Modifier.alpha(0.5f)) {
        TextField(
            value = textState,
            onValueChange = {
                if (it.text.trim().length == count){
                    //keyboardController?.hideSoftwareKeyboard()
                }
                val te = when {
                    it.text.trim().length > previewText.trim().length -> {
                        it.copy(text = previewText + (it.text.lastOrNull() ?: ""))
                    }
                    it.text.trim().length < previewText.trim().length -> {
                        it.copy(text = previewText.dropLast(1))
                    }
                    else -> {
                        it
                    }
                }
                previewText = te.text.trim()
                textState = te.copy(text = te.text.trim().take(count))
            },
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
        )
    }
    DisposableEffect({ }) {
        focusRequester.requestFocus()
        onDispose { }
    }
}

@Composable
private fun Empty(onClick: () -> Unit) {
    Square(content = {  }, color = Color.Gray) {

    }
}

@Composable
private fun Filled(onClick: () -> Unit) {
    Square(content = { Text(text = "*", Modifier.fillMaxSize()) }, color = Color.Cyan) {

    }
}

@Composable
private fun Square(content: @Composable () -> Unit, color: Color, onClick: () -> Unit) {
    Box(
        Modifier
            .padding(16.dp)
            .background(color)
            .width(75.dp)
            .height(48.dp)
    ) {
        content.invoke()
    }
}