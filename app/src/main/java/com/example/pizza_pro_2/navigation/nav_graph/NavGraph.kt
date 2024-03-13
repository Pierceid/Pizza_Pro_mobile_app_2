package com.example.pizza_pro_2.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pizza_pro_2.navigation.HOME_GRAPH_ROUTE
import com.example.pizza_pro_2.navigation.HomeScreen
import com.example.pizza_pro_2.navigation.ROOT_GRAPH_ROUTE
import com.example.pizza_pro_2.navigation.Screen
import com.example.pizza_pro_2.screen.IntroScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Intro.route,
        route = ROOT_GRAPH_ROUTE
    ) {
        composable(Screen.Intro.route) {
            IntroScreen(navController = navController)
        }
        authNavGraph(navController = navController)
        composable(HOME_GRAPH_ROUTE) {
            HomeScreen()
        }
    }
}