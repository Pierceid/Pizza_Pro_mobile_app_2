package com.example.pizza_pro_2.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.shared.SharedFormEvent
import com.example.pizza_pro_2.domain.shared.SharedFormState
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.ui.theme.Lilac
import com.example.pizza_pro_2.ui.theme.White

@Composable
fun SettingsScreen(
    navController: NavController,
    sharedState: SharedFormState,
    onSharedEvent: (SharedFormEvent) -> Unit
) {
    DefaultColumn {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(border = BorderStroke(width = 1.dp, color = White))
                    .clickable {

                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier
                            .size(56.dp)
                            .padding(12.dp),
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = stringResource(id = R.string.account),
                        tint = Lilac
                    )

                    Text(
                        text = stringResource(id = R.string.account),
                        style = MaterialTheme.typography.titleLarge,
                        color = Lilac
                    )
                }

                Icon(
                    modifier = Modifier
                        .size(56.dp)
                        .padding(12.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(id = R.string.account),
                    tint = Lilac
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(border = BorderStroke(width = 1.dp, color = White))
                    .clickable {

                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier
                            .size(56.dp)
                            .padding(12.dp),
                        painter = painterResource(id = R.drawable.history_24),
                        contentDescription = stringResource(id = R.string.history),
                        tint = Lilac
                    )

                    Text(
                        text = stringResource(id = R.string.history),
                        style = MaterialTheme.typography.titleLarge,
                        color = Lilac
                    )
                }

                Icon(
                    modifier = Modifier
                        .size(56.dp)
                        .padding(12.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(id = R.string.history),
                    tint = Lilac
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(border = BorderStroke(width = 1.dp, color = White))
                    .clickable {

                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier
                            .size(56.dp)
                            .padding(12.dp),
                        imageVector = Icons.Default.Info,
                        contentDescription = stringResource(id = R.string.about_app),
                        tint = Lilac
                    )

                    Text(
                        text = stringResource(id = R.string.about_app),
                        style = MaterialTheme.typography.titleLarge,
                        color = Lilac
                    )
                }

                Icon(
                    modifier = Modifier
                        .size(56.dp)
                        .padding(12.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(id = R.string.about_app),
                    tint = Lilac
                )
            }
        }
    }
}
