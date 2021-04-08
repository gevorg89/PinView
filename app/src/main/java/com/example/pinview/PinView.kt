package com.example.pinview

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Composable
fun PinView(count: Int) {
    val positionText by remember { mutableStateOf(mutableListOf<String>()) }
    var filledCount by remember { mutableStateOf(positionText.size) }
    var isAdded by remember { mutableStateOf(false) }
    require(filledCount <= count) {
        "filled count more then count pin views"
    }
    Row() {
        repeat(filledCount) { position ->
            Filled(
                initText = positionText.getOrNull(position) ?: "",
                isFocused = isFocused(position, if (isAdded) filledCount else filledCount - 1)
            ) { newText ->
                positionText.removeAt(position)
                checkPosition(
                    maxCount = count,
                    filledCount = filledCount,
                    text = newText,
                    onChange = {
                        isAdded = false
                        filledCount -= 1
                    })
            }
        }
        repeat(count - filledCount) { position ->
            val calcPosition = filledCount + position
            Empty(
                initText = positionText.getOrNull(calcPosition) ?: ""
            ) { newText ->
                positionText.add(newText)
                checkPosition(
                    maxCount = count,
                    filledCount = filledCount,
                    text = newText,
                    onChange = {
                        isAdded = true
                        filledCount = it
                    })
            }
        }
    }

}

private fun isFocused(position: Int, filledCount: Int) = (position == filledCount).also {
    Log.d("isFocused", "position=$position, filledCount=$filledCount, result=$it")
}

private fun checkPosition(maxCount: Int, filledCount: Int, text: String, onChange: (Int) -> Unit) {
    val newPosition = if (text.isNotEmpty()) {
        if (filledCount == maxCount) {
            filledCount
        } else {
            filledCount + 1
        }
    } else {
        if (filledCount > 0) {
            filledCount - 1
        } else {
            0
        }
    }
    onChange.invoke(newPosition)
}

@Composable
private fun Empty(initText: String, onChange: (String) -> Unit) {
    TextFiledInner(initText, isFocused = false) { text: String ->
        onChange(text)
    }
}

@Composable
private fun Filled(initText: String, isFocused: Boolean, onChange: (String) -> Unit) {
    Box(Modifier.background(Color.Cyan)) {
        TextFiledInner(initText, isFocused) { text: String ->
            onChange(text)
        }
    }
}

@Composable
private fun TextFiledInner(initText: String, isFocused: Boolean, onChange: (String) -> Unit) {
    var textState by remember {
        mutableStateOf(
            TextFieldValue(
                text = initText,
                selection = TextRange(initText.length),
                composition = TextRange(initText.length)
            )
        )
    }
    val focusRequester = FocusRequester()
    var previewInvoked = textState.text
    Box(Modifier.width(75.dp)) {
        TextField(
            value = textState,
            onValueChange = {
                textState = it
                if (initText != it.text) {
                    if (previewInvoked != it.text) {
                        previewInvoked = it.text
                        textState = it.copy(text = "")
                        onChange.invoke(it.text)
                    }
                }
            },
            modifier = Modifier
                .focusRequester(focusRequester)
        )
    }
    if (isFocused)
        DisposableEffect({ }) {
            focusRequester.requestFocus()
            onDispose { }
        }
}