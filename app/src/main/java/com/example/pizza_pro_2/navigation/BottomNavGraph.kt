package com.example.pizza_pro_2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pizza_pro_2.screen.CartScreen
import com.example.pizza_pro_2.screen.FeedbackScreen
import com.example.pizza_pro_2.screen.ProfileScreen
import com.example.pizza_pro_2.screen.ShopScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Shop.route,
        route = HOME_GRAPH_ROUTE
    ) {
        composable(route = Screen.Shop.route) {
            ShopScreen(navController = navController)
        }
        composable(route = Screen.Cart.route) {
            CartScreen(navController = navController)
        }
        composable(route = Screen.Feedback.route) {
            FeedbackScreen(navController = navController)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}