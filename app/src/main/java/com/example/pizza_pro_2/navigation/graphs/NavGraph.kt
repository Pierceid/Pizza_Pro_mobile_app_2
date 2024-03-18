package com.example.pizza_pro_2.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pizza_pro_2.presentation.screens.AUTH_GRAPH_ROUTE
import com.example.pizza_pro_2.presentation.screens.HOME_GRAPH_ROUTE
import com.example.pizza_pro_2.navigation.HomeScreen
import com.example.pizza_pro_2.presentation.screens.ROOT_GRAPH_ROUTE

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AUTH_GRAPH_ROUTE,
        route = ROOT_GRAPH_ROUTE
    ) {
        authNavGraph(navController = navController)
        composable(HOME_GRAPH_ROUTE) {
            HomeScreen()
        }
    }
}