package com.example.pizza_pro_2.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pizza_pro_2.navigation.HomeScreen
import com.example.pizza_pro_2.options.GraphRoute

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = GraphRoute.AuthGraph.name,
        route = GraphRoute.RootGraph.name
    ) {
        authNavGraph(navController = navController)
        composable(GraphRoute.HomeGraph.name) {
            HomeScreen()
        }
    }
}
