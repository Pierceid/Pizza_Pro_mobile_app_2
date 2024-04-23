package com.example.pizza_pro_2.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pizza_pro_2.domain.shared.SharedEvent
import com.example.pizza_pro_2.domain.shared.SharedState
import com.example.pizza_pro_2.options.GraphRoute
import com.example.pizza_pro_2.presentation.screens.AboutAppScreen
import com.example.pizza_pro_2.presentation.screens.AccountScreen
import com.example.pizza_pro_2.presentation.screens.HistoryScreen
import com.example.pizza_pro_2.presentation.screens.Screen
import com.example.pizza_pro_2.presentation.screens.SettingsScreen

fun NavGraphBuilder.settingsNavGraph(
    navController: NavHostController,
    sharedState: SharedState,
    onSharedEvent: (SharedEvent) -> Unit
) {
    navigation(
        startDestination = Screen.Settings.route,
        route = GraphRoute.SettingsGraph.name
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
