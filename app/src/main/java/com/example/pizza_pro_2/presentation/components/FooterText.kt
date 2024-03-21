package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.example.pizza_pro_2.ui.theme.Magenta
import com.example.pizza_pro_2.ui.theme.White

@Stable
@Composable
fun FooterText(
    text: String,
    hyperText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = White
        )
        TextButton(onClick = onClick) {
            Text(
                text = hyperText,
                style = MaterialTheme.typography.titleMedium,
                color = Magenta,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}