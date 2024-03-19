package com.example.pizza_pro_2.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.pizza_pro_2.ui.theme.Magenta

@Composable
fun HeaderText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    color: Color = Magenta
) {
    Text(modifier = modifier, text = text, style = textStyle, color = color)
}