package com.example.pizza_pro_2.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pizza_pro_2.database.MyDao
import com.example.pizza_pro_2.domain.shared.SharedViewModel
import com.example.pizza_pro_2.navigation.HomeScreen
import com.example.pizza_pro_2.presentation.screens.AUTH_GRAPH_ROUTE
import com.example.pizza_pro_2.presentation.screens.HOME_GRAPH_ROUTE
import com.example.pizza_pro_2.presentation.screens.ROOT_GRAPH_ROUTE

@Composable
fun NavGraph(navController: NavHostController, myDao: MyDao) {
    val sharedViewModel: SharedViewModel = viewModel()
    val sharedState by sharedViewModel.state.collectAsState()
    val onSharedEvent = sharedViewModel::onEvent

    NavHost(
        navController = navController,
        startDestination = AUTH_GRAPH_ROUTE,
        route = ROOT_GRAPH_ROUTE
    ) {
        authNavGraph(
            navController = navController,
            sharedState = sharedState,
            onSharedEvent = onSharedEvent,
            myDao = myDao
        )
        composable(HOME_GRAPH_ROUTE) {
            HomeScreen(
                sharedState = sharedState,
                onSharedEvent = onSharedEvent,
                myDao = myDao
            )
        }
    }
}
