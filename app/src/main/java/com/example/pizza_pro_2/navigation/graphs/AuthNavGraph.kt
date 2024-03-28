package com.example.pizza_pro_2.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.pizza_pro_2.presentation.screens.AUTH_GRAPH_ROUTE
import com.example.pizza_pro_2.presentation.screens.Screen
import com.example.pizza_pro_2.presentation.screens.IntroScreen
import com.example.pizza_pro_2.presentation.screens.SignInScreen
import com.example.pizza_pro_2.presentation.screens.SignUpScreen
import com.example.pizza_pro_2.domain.shared.SharedViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController, sharedViewModel: SharedViewModel) {
    navigation(
        startDestination = Screen.Intro.route,
        route = AUTH_GRAPH_ROUTE
    ) {
        composable(route = Screen.Intro.route) {
            IntroScreen(navController = navController)
        }
        composable(route = Screen.SignUp.route) {
            SignUpScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = Screen.SignIn.route) {
            SignInScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
    }
}