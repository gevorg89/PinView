package com.example.pinview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue

data class PinData(
    val complete: Boolean = false,
    val text: String
)

@ExperimentalComposeUiApi
@Composable
fun PinView(
    modifier: Modifier = Modifier,
    count: Int,
    empty: @Composable (Int) -> Unit,
    filled: @Composable (Char, Int) -> Unit,
    keyboardType: KeyboardType = KeyboardType.NumberPassword,
    onChange: (PinData) -> Unit
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
    Box(modifier) {
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            value = textState,
            onValueChange = { newTextState ->
                var isComplete = false
                if (newTextState.text.trim().length >= count && textState.text.length >= count - 1) {
                    if (newTextState.text != textState.text) {
                        keyboardController?.hideSoftwareKeyboard()
                    }
                    isComplete = true
                }
                val newText = newTextState.text.trim().take(count)
                textState = newTextState.copy(text = newText, selection = TextRange(newText.length))
                onChange.invoke(PinData(complete = isComplete, text = textState.text))
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .matchParentSize()
                .alpha(0.0f)
                .focusRequester(focusRequester)
        )
        Row(Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    focusRequester.requestFocus()
                    keyboardController?.showSoftwareKeyboard()
                }
            )) {
            repeat(textState.text.length) { position ->
                filled(textState.text.last(), position)
            }
            repeat(count - textState.text.length) { position ->
                empty(position + textState.text.length)
            }
        }
    }
    DisposableEffect({ }) {
        focusRequester.requestFocus()
        onDispose { }
    }
}