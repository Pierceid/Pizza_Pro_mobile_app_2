package com.example.pizza_pro_2.presentation.components

import androidx.annotation.StringRes
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import com.example.pizza_pro_2.ui.theme.Pink
import com.example.pizza_pro_2.ui.theme.White

@Stable
@Composable
fun FooterText(
    @StringRes textId: Int,
    @StringRes hypertextId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = stringResource(textId),
            style = MaterialTheme.typography.titleSmall,
            color = White
        )
        TextButton(onClick = onClick) {
            Text(
                text = stringResource(hypertextId),
                style = MaterialTheme.typography.titleSmall,
                color = Pink,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}