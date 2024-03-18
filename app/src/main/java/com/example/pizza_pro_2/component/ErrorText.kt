package com.example.pizza_pro_2.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pizza_pro_2.ui.theme.Salmon

@Composable
fun ErrorText(message: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = message,
        color = Salmon,
        style = MaterialTheme.typography.titleSmall
    )
}