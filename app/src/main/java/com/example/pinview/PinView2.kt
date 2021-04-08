package com.example.pinview

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Composable
fun PinView2(
    count: Int,
    empty: @Composable () -> Unit,
    filled: @Composable () -> Unit,
    keyboardType: KeyboardType = KeyboardType.NumberPassword
) {
    var textState by remember {
        mutableStateOf(
            TextFieldValue(
                text = ""
            )
        )
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = FocusRequester()
    Row(
        Modifier
            .padding(top = 0.dp)
    ) {
        repeat(textState.text.length) {
            filled()
        }
        repeat(count - textState.text.length) {
            empty()
        }
    }
    Row(Modifier.alpha(0.0f)) {
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            value = textState,
            onValueChange = { newTextState ->
                if (newTextState.text.trim().length == count && textState.text.length == count - 1) {
                    keyboardController?.hideSoftwareKeyboard()
                }
                val newText = newTextState.text.trim().take(count)
                textState = newTextState.copy(text = newText, selection = TextRange(newText.length))
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