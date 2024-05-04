package com.example.pizza_pro_2.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pizza_pro_2.options.GraphRoute
import com.example.pizza_pro_2.presentation.screens.AboutAppScreen
import com.example.pizza_pro_2.presentation.screens.AccountScreen
import com.example.pizza_pro_2.presentation.screens.HistoryScreen
import com.example.pizza_pro_2.presentation.screens.Screen
import com.example.pizza_pro_2.presentation.screens.SettingsScreen

fun NavGraphBuilder.settingsNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.Settings.route,
        route = GraphRoute.SettingsGraph.name
    ) {
        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(route = Screen.Account.route) {
            AccountScreen()
        }
        composable(route = Screen.History.route) {
            HistoryScreen()
        }
        composable(route = Screen.AboutApp.route) {
            AboutAppScreen()
        }
    }
}
