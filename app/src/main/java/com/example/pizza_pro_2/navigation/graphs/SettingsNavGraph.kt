package com.example.pizza_pro_2.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pizza_pro_2.domain.shared.SharedFormEvent
import com.example.pizza_pro_2.domain.shared.SharedFormState
import com.example.pizza_pro_2.presentation.screens.AboutAppScreen
import com.example.pizza_pro_2.presentation.screens.HistoryScreen
import com.example.pizza_pro_2.presentation.screens.AccountScreen
import com.example.pizza_pro_2.presentation.screens.SETTINGS_GRAPH_ROUTE
import com.example.pizza_pro_2.presentation.screens.Screen
import com.example.pizza_pro_2.presentation.screens.SettingsScreen

fun NavGraphBuilder.settingsNavGraph(
    navController: NavHostController,
    sharedState: SharedFormState,
    onSharedEvent: (SharedFormEvent) -> Unit
) {
    navigation(
        startDestination = Screen.Settings.route,
        route = SETTINGS_GRAPH_ROUTE
    ) {
        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController, sharedState = sharedState, onSharedEvent = onSharedEvent)
        }
        composable(route = Screen.Account.route) {
            AccountScreen(navController = navController, sharedState = sharedState, onSharedEvent = onSharedEvent)
        }
        composable(route = Screen.History.route) {
            HistoryScreen(navController = navController, sharedState = sharedState, onSharedEvent = onSharedEvent)
        }
        composable(route = Screen.AboutApp.route) {
            AboutAppScreen(navController = navController, sharedState = sharedState, onSharedEvent = onSharedEvent)
        }
    }
}
