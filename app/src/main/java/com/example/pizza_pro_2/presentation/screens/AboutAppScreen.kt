package com.example.pizza_pro_2.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.ui.theme.Pink
import com.example.pizza_pro_2.ui.theme.Silver
import com.example.pizza_pro_2.ui.theme.White

@Composable
fun AboutAppScreen() {
    DefaultColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = stringResource(R.string.app_version),
            style = MaterialTheme.typography.titleSmall,
            color = Pink,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.app_copyright),
            style = MaterialTheme.typography.bodyLarge,
            color = White,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.app_description),
            style = MaterialTheme.typography.bodyLarge,
            color = Silver,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.app_highlights),
            style = MaterialTheme.typography.bodyLarge,
            color = White,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.app_support),
            style = MaterialTheme.typography.bodyLarge,
            color = Silver,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.app_thanks),
            style = MaterialTheme.typography.bodyLarge,
            color = White,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
