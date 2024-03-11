package com.example.pizza_pro_2.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.pizza_pro_2.screen.CartScreen
import com.example.pizza_pro_2.screen.FeedbackScreen
import com.example.pizza_pro_2.screen.ShopScreen

fun NavGraphBuilder.homeNavGraph(navHostController: NavHostController) {
    navigation(
        startDestination = Screen.Shop.route,
        route = HOME_GRAPH_ROUTE
    ) {
        composable(route = Screen.Shop.route) {
            ShopScreen(navController = navHostController)
        }
        composable(route = Screen.Cart.route) {
            CartScreen(navController = navHostController)
        }
        composable(route = Screen.Feedback.route) {
            FeedbackScreen(navController = navHostController)
        }
    }
}