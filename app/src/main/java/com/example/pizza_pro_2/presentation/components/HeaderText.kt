package com.example.pizza_pro_2.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.pizza_pro_2.ui.theme.Pink

@Stable
@Composable
fun HeaderText(
    @StringRes textId: Int,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    color: Color = Pink
) {
    Text(modifier = modifier, text = stringResource(textId), style = textStyle, color = color)
}