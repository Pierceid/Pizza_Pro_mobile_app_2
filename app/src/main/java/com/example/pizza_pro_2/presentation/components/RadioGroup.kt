package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.ui.theme.Lime
import com.example.pizza_pro_2.ui.theme.Salmon
import com.example.pizza_pro_2.ui.theme.White

@Composable
fun <T> RadioGroup(
    selected: T,
    onSelectionChange: (T) -> Unit,
    options: List<T>,
    type: Int,
    modifier: Modifier = Modifier,
    imagePainterIds: List<Int> = emptyList(),
    imageSize: Dp = 52.dp
) {
    var selectedOption by rememberSaveable { mutableStateOf(selected) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = selectedOption == option

            when (type) {
                0 -> {
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

                1 -> {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .background(if (isSelected) Lime else Salmon, RoundedCornerShape(8.dp))
                            .border(BorderStroke(2.dp, White), RoundedCornerShape(8.dp))
                            .padding(horizontal = 6.dp, vertical = 10.dp)
                            .clickable {
                                selectedOption = option
                                onSelectionChange(option)
                            },
                        text = option.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = White,
                        textAlign = TextAlign.Center
                    )

                    if (index != options.lastIndex) {
                        Spacer(Modifier.width(8.dp))
                    }
                }

                2 -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(
                            modifier = Modifier.size(imageSize + 12.dp),
                            onClick = {
                                selectedOption = option
                                onSelectionChange(option)
                            }
                        ) {
                            Image(
                                modifier = Modifier.size(imageSize).padding(6.dp),
                                painter = painterResource(
                                    imagePainterIds.getOrElse(index) { R.drawable.image_not_found }
                                ),
                                contentDescription = stringResource(R.string.option_image),
                                contentScale = ContentScale.FillBounds
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
                }

                else -> {}
            }
        }
    }
}
