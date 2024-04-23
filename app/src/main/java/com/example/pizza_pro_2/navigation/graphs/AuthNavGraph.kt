package com.example.pizza_pro_2.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.pizza_pro_2.domain.shared.SharedEvent
import com.example.pizza_pro_2.domain.shared.SharedState
import com.example.pizza_pro_2.options.GraphRoute
import com.example.pizza_pro_2.presentation.screens.IntroScreen
import com.example.pizza_pro_2.presentation.screens.Screen
import com.example.pizza_pro_2.presentation.screens.SignInScreen
import com.example.pizza_pro_2.presentation.screens.SignUpScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    sharedState: SharedState,
    onSharedEvent: (SharedEvent) -> Unit
) {
    navigation(
        startDestination = Screen.Intro.route,
        route = GraphRoute.AuthGraph.name
    ) {
        composable(route = Screen.Intro.route) {
            IntroScreen(navController = navController)
        }
        composable(route = Screen.SignUp.route) {
            SignUpScreen(
                navController = navController,
                sharedState = sharedState,
                onSharedEvent = onSharedEvent
            )
        }
        composable(route = Screen.SignIn.route) {
            SignInScreen(
                navController = navController,
                sharedState = sharedState,
                onSharedEvent = onSharedEvent
            )
        }
    }
}
