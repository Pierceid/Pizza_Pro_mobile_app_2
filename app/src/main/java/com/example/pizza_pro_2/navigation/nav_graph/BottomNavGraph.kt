package com.example.pizza_pro_2.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pizza_pro_2.navigation.DETAIL_GRAPH_ROUTE
import com.example.pizza_pro_2.navigation.HOME_GRAPH_ROUTE
import com.example.pizza_pro_2.navigation.PROFILE_GRAPH_ROUTE
import com.example.pizza_pro_2.navigation.Screen
import com.example.pizza_pro_2.screen.CartScreen
import com.example.pizza_pro_2.screen.DetailScreen
import com.example.pizza_pro_2.screen.FeedbackScreen
import com.example.pizza_pro_2.screen.ProfileScreen
import com.example.pizza_pro_2.screen.SettingsScreen
import com.example.pizza_pro_2.screen.ShopScreen
import com.example.pizza_pro_2.view_model.SharedViewModel

@Composable
fun BottomNavGraph(navController: NavHostController) {
    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Shop.route,
        route = HOME_GRAPH_ROUTE
    ) {
        composable(route = Screen.Shop.route) {
            ShopScreen(navController = navController, sharedViewModel)
        }
        composable(route = Screen.Cart.route) {
            CartScreen(navController = navController)
        }
        composable(route = Screen.Feedback.route) {
            FeedbackScreen(navController = navController)
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        navigation(
            startDestination = Screen.Detail.route,
            route = DETAIL_GRAPH_ROUTE
        ) {
            composable(route = Screen.Detail.route) {
                DetailScreen(navController, sharedViewModel)
            }
        }
        navigation(
            startDestination = Screen.Profile.route,
            route = PROFILE_GRAPH_ROUTE
        ) {
            composable(route = Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}