package com.example.pizza_pro_2.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.pizza_pro_2.domain.shared.SharedFormEvent
import com.example.pizza_pro_2.domain.shared.SharedFormState
import com.example.pizza_pro_2.presentation.components.DefaultColumn

@Composable
fun ProfileScreen(
    navController: NavController,
    sharedState: SharedFormState,
    onSharedEvent: (SharedFormEvent) -> Unit
) {
    DefaultColumn {

    }
}
