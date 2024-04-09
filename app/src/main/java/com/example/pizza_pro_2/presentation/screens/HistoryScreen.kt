package com.example.pizza_pro_2.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.shared.SharedFormEvent
import com.example.pizza_pro_2.domain.shared.SharedFormState
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.HeaderText

@Composable
fun HistoryScreen(
    navController: NavController,
    sharedState: SharedFormState,
    onSharedEvent: (SharedFormEvent) -> Unit
) {
    DefaultColumn {
        HeaderText(text = stringResource(id = R.string.history))
    }
}
