package com.example.pizza_pro_2.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pizza_pro_2.screen.CartScreen
import com.example.pizza_pro_2.screen.FeedbackScreen
import com.example.pizza_pro_2.screen.ShopScreen
import com.example.pizza_pro_2.ui.theme.Blue

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        modifier = Modifier.background(Blue),
        navController = navController,
        startDestination = Screen.Shop.route,
        route = HOME_GRAPH_ROUTE
    ) {
        composable(route = Screen.Shop.route) {
            ShopScreen()
        }
        composable(route = Screen.Cart.route) {
            CartScreen()
        }
        composable(route = Screen.Feedback.route) {
            FeedbackScreen()
        }
    }
}