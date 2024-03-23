package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.ui.theme.White

@Composable
fun <T> RadioGroup(
    inRow: Boolean,
    selected: T,
    onSelectionChange: (T) -> Unit,
    options: List<T>,
    modifier: Modifier = Modifier,
    iconPainter: Painter? = null,
    iconColor: Color = White
) {
    val selectedOption = remember { mutableStateOf(selected) }

    Row(
        modifier = modifier.fillMaxWidth().padding(end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (inRow) {
            options.forEach { option ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedOption.value == option,
                        onClick = {
                            selectedOption.value = option
                            onSelectionChange(selectedOption.value)
                        }
                    )
                    Text(
                        modifier = Modifier.clickable {
                            selectedOption.value = option
                            onSelectionChange(selectedOption.value)
                        },
                        text = option.toString(),
                        style = MaterialTheme.typography.titleSmall,
                        color = White
                    )
                }
            }
        } else {
            Column {
                options.forEach { option ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedOption.value == option,
                            onClick = {
                                selectedOption.value = option
                                onSelectionChange(selectedOption.value)
                            }
                        )
                        Text(
                            modifier = Modifier.clickable {
                                selectedOption.value = option
                                onSelectionChange(selectedOption.value)
                            },
                            text = option.toString(),
                            style = MaterialTheme.typography.titleSmall,
                            color = White
                        )
                    }
                }
            }

            iconPainter?.let {
                Icon(
                    painter = iconPainter,
                    tint = iconColor,
                    contentDescription = stringResource(id = R.string.satisfaction_image)
                )
            }
        }
    }
}
