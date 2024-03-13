package com.example.pizza_pro_2.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.pizza_pro_2.navigation.AUTH_GRAPH_ROUTE
import com.example.pizza_pro_2.navigation.Screen
import com.example.pizza_pro_2.screen.LoginScreen
import com.example.pizza_pro_2.screen.RegisterScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.Register.route,
        route = AUTH_GRAPH_ROUTE
    ) {
        composable(route = Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
    }
}