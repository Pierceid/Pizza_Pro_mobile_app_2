package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun InfoDialog(
    title: String,
    text: String,
    onDismiss: (Boolean) -> Unit,
    dismissButton: Int? = null,
    onConfirm: () -> Unit = { },
    confirmButton: Int? = null,
) {
    AlertDialog(
        onDismissRequest = { onDismiss(false) },
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
        confirmButton = {
            confirmButton?.let {
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss(false)
                    }
                ) {
                    Text(
                        text = stringResource(id = it),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        },
        dismissButton = {
            dismissButton?.let {
                OutlinedButton(onClick = { onDismiss(false) }) {
                    Text(
                        text = stringResource(id = it),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    )
}
