package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import com.example.pizza_pro_2.R

@Composable
fun InfoDialog(
    title: String,
    text: String,
    onDismiss: (Boolean) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss(false) },
        confirmButton = { },
        title = {
            Text(
                text = title,
                textDecoration = TextDecoration.Underline
            )
        },
        text = {
            Text(
                modifier = Modifier.verticalScroll(state = rememberScrollState(), enabled = true),
                text = text
            )
        },
        dismissButton = {
            Button(onClick = { onDismiss(false) }) {
                Text(
                    text = stringResource(id = R.string.ok),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    )
}
