package com.example.pizza_pro_2.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pizza_pro_2.database.MyRepository
import com.example.pizza_pro_2.domain.shared.SharedFormEvent
import com.example.pizza_pro_2.domain.shared.SharedFormState
import com.example.pizza_pro_2.options.GraphRoute
import com.example.pizza_pro_2.presentation.screens.CartScreen
import com.example.pizza_pro_2.presentation.screens.FeedbackScreen
import com.example.pizza_pro_2.presentation.screens.Screen
import com.example.pizza_pro_2.presentation.screens.ShopScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    sharedState: SharedFormState,
    onSharedEvent: (SharedFormEvent) -> Unit,
    myRepository: MyRepository
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Shop.route,
        route = GraphRoute.HomeGraph.name
    ) {
        composable(route = Screen.Shop.route) {
            ShopScreen(
                navController = navController,
                sharedState = sharedState,
                onSharedEvent = onSharedEvent
            )
        }
        composable(route = Screen.Cart.route) {
            CartScreen(
                navController = navController,
                sharedState = sharedState,
                onSharedEvent = onSharedEvent
            )
        }
        composable(route = Screen.Feedback.route) {
            FeedbackScreen(navController = navController)
        }
        settingsNavGraph(
            navController = navController,
            sharedState = sharedState,
            onSharedEvent = onSharedEvent,
            myRepository = myRepository
        )
    }
}