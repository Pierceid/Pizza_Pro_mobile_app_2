package com.example.pizza_pro_2.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pizza_pro_2.presentation.screens.CartScreen
import com.example.pizza_pro_2.presentation.screens.DETAIL_GRAPH_ROUTE
import com.example.pizza_pro_2.presentation.screens.DetailScreen
import com.example.pizza_pro_2.presentation.screens.FeedbackScreen
import com.example.pizza_pro_2.presentation.screens.HOME_GRAPH_ROUTE
import com.example.pizza_pro_2.presentation.screens.PROFILE_GRAPH_ROUTE
import com.example.pizza_pro_2.presentation.screens.ProfileScreen
import com.example.pizza_pro_2.presentation.screens.Screen
import com.example.pizza_pro_2.presentation.screens.SettingsScreen
import com.example.pizza_pro_2.presentation.screens.ShopScreen
import com.example.pizza_pro_2.view_models.SharedViewModel

@Composable
fun BottomNavGraph(navController: NavHostController, sharedViewModel: SharedViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Shop.route,
        route = HOME_GRAPH_ROUTE
    ) {
        composable(route = Screen.Shop.route) {
            ShopScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.Cart.route) {
            CartScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.Feedback.route) {
            FeedbackScreen(navController = navController)
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        navigation(
            startDestination = Screen.Detail.route,
            route = DETAIL_GRAPH_ROUTE
        ) {
            composable(route = Screen.Detail.route) {
                DetailScreen(navController = navController, sharedViewModel = sharedViewModel)
            }
        }
        navigation(
            startDestination = Screen.Profile.route,
            route = PROFILE_GRAPH_ROUTE
        ) {
            composable(route = Screen.Profile.route) {
                ProfileScreen(navController = navController, sharedViewModel = sharedViewModel)
            }
        }
    }
}