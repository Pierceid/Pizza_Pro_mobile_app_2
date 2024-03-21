package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier

@Stable
@Composable
fun ErrorText(message: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = message,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.titleSmall
    )
}