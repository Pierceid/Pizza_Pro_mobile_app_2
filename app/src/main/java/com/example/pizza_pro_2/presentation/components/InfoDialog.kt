package com.example.pizza_pro_2.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.ui.theme.Slate

@Composable
fun InfoDialog(
    @StringRes titleId: Int,
    @StringRes textId: Int,
    onDismiss: () -> Unit,
    dismissButton: Int? = null,
    onConfirm: () -> Unit = { },
    confirmButton: Int? = null,
    isError: Boolean = false,
    errorMessageId: Int? = null,
    hasInputField: Boolean = false,
    inputFieldValue: String = "",
    onInputFieldValueChange: (String) -> Unit = { },
    color: Color = Slate
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(titleId),
                textDecoration = TextDecoration.Underline
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    modifier = Modifier.verticalScroll(state = rememberScrollState(), enabled = true),
                    text = stringResource(textId),
                )

                if (hasInputField) {
                    InputTextField(
                        value = inputFieldValue,
                        onValueChange = onInputFieldValueChange,
                        labelId = R.string.place,
                        isError = isError,
                        leadingIcon = Icons.Default.LocationOn,
                        trailingIcon = Icons.Default.Clear,
                        onTrailingIconClick = { onInputFieldValueChange("") }
                    )
                    errorMessageId?.let {
                        ErrorText(messageId = it)
                    }
                }
            }
        },
        confirmButton = {
            confirmButton?.let {
                Button(onClick = onConfirm) {
                    Text(
                        text = stringResource(it),
                        style = MaterialTheme.typography.bodyMedium,
                        color = color
                    )
                }
            }
        },
        dismissButton = {
            dismissButton?.let {
                OutlinedButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(it),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        containerColor = color
    )
}
