package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.ui.theme.White

@Composable
fun InputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onLeadingIconClick: () -> Unit = { },
    onTrailingIconClick: () -> Unit = { },
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        isError = isError,
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = stringResource(id = R.string.search),
                    tint = if (isError) MaterialTheme.colorScheme.error else White,
                    modifier = Modifier.clickable { onLeadingIconClick() }
                )
            }
        },
        trailingIcon = {
            trailingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = stringResource(id = R.string.clear),
                    tint = if (isError) MaterialTheme.colorScheme.error else White,
                    modifier = Modifier.clickable { onTrailingIconClick() }
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(16.dp)
    )
}
