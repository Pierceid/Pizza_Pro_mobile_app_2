package com.example.pizza_pro_2.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.ui.theme.Black

@Composable
fun <T> RadioGroup(
    label: String,
    options: List<T>,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Black, RoundedCornerShape(16.dp)),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) Icon(imageVector = icon, contentDescription = label)

            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Column {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = { selectedOption = option }
                    )
                    Text(text = option.toString(), style = MaterialTheme.typography.titleSmall)
                }
            }
        }
    }
}