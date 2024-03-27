package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.ui.theme.White

@Composable
fun <T> RadioGroup(
    selected: T,
    onSelectionChange: (T) -> Unit,
    choices: List<T>,
    modifier: Modifier = Modifier,
    imagePainterIds: List<Int> = emptyList()
) {
    var selectedOption by rememberSaveable { mutableStateOf(selected) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        choices.forEachIndexed { index, option ->
            val isSelected = selectedOption == option

            if (imagePainterIds.isNotEmpty()) {
                Spacer(modifier = Modifier.width(16.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(
                        onClick = {
                            selectedOption = option
                            onSelectionChange(option)
                        }
                    ) {
                        Image(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(id = imagePainterIds.getOrElse(index) { -1 }),
                            contentDescription = stringResource(id = R.string.satisfaction_image)
                        )
                    }

                    if (isSelected) {
                        Text(
                            text = option.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = White
                        )
                    }
                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = isSelected,
                        onClick = {
                            selectedOption = option
                            onSelectionChange(option)
                        }
                    )

                    Text(
                        modifier = Modifier
                            .clickable {
                                selectedOption = option
                                onSelectionChange(option)
                            },
                        text = option.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None,
                        color = White
                    )
                }
            }
        }
    }
}
