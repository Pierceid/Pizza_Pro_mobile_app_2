package com.example.pizza_pro_2.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.pizza_pro_2.database.MyDao
import com.example.pizza_pro_2.domain.shared.SharedFormEvent
import com.example.pizza_pro_2.domain.shared.SharedFormState
import com.example.pizza_pro_2.presentation.screens.AUTH_GRAPH_ROUTE
import com.example.pizza_pro_2.presentation.screens.IntroScreen
import com.example.pizza_pro_2.presentation.screens.Screen
import com.example.pizza_pro_2.presentation.screens.SignInScreen
import com.example.pizza_pro_2.presentation.screens.SignUpScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    sharedState: SharedFormState,
    onSharedEvent: (SharedFormEvent) -> Unit,
    myDao: MyDao
) {
    navigation(
        startDestination = Screen.Intro.route,
        route = AUTH_GRAPH_ROUTE
    ) {
        composable(route = Screen.Intro.route) {
            IntroScreen(navController = navController)
        }
        composable(route = Screen.SignUp.route) {
            SignUpScreen(
                navController = navController,
                sharedState = sharedState,
                onSharedEvent = onSharedEvent,
                myDao = myDao
            )
        }
        composable(route = Screen.SignIn.route) {
            SignInScreen(
                navController = navController,
                sharedState = sharedState,
                onSharedEvent = onSharedEvent,
                myDao = myDao
            )
        }
    }
}
