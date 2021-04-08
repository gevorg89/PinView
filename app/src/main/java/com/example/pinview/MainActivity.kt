package com.example.pinview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pinview.ui.theme.PinViewTheme

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PinViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //PinView()
                    DefaultPreview()
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    PinViewTheme {
        PinView2(count = 4, empty = { EmptyCircle() }, filled = { FilledCircle() })
    }
}


@Composable
private fun EmptyCircle() {
    Circle(content = { }, color = Color.Gray)
}

@Composable
private fun FilledCircle() {
    Circle(content = {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "*", fontSize = 32.sp)
        }
    }, color = Color.Cyan)
}

@Composable
private fun Circle(content: @Composable () -> Unit, color: Color) {
    Column(
        Modifier
            .padding(8.dp)
            .size(72.dp)
            .clip(CircleShape)
            .background(color)

    ) {
        content.invoke()
    }
}


@Composable
private fun EmptySquare() {
    Square(content = { }, color = Color.Gray)
}

@Composable
private fun FilledSquare() {
    Square(content = { Text(text = "*", Modifier.fillMaxSize()) }, color = Color.Cyan)
}

@Composable
private fun Square(content: @Composable () -> Unit, color: Color) {
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







