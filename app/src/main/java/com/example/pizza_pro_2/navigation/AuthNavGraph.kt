package com.example.pizza_pro_2.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.pizza_pro_2.screen.LoginScreen
import com.example.pizza_pro_2.screen.RegisterScreen

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController) {
    navigation(
        startDestination = Screen.Register.route,
        route = AUTH_GRAPH_ROUTE
    ) {
        composable(route = Screen.Register.route) {
            RegisterScreen(navController = navHostController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navHostController)
        }
    }
}