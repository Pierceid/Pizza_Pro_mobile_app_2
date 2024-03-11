package com.example.pizza_pro_2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pizza_pro_2.screen.IntroScreen

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Intro.route,
        route = ROOT_GRAPH_ROUTE
    ) {
        composable(Screen.Intro.route) {
            IntroScreen(navController = navHostController)
        }
        authNavGraph(navHostController = navHostController)
        homeNavGraph(navHostController = navHostController)
    }
}