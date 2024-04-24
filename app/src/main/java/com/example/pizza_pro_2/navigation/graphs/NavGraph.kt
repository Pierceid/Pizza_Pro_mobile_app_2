package com.example.pizza_pro_2.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pizza_pro_2.presentation.MyViewModelProvider
import com.example.pizza_pro_2.domain.shared.SharedViewModel
import com.example.pizza_pro_2.navigation.HomeScreen
import com.example.pizza_pro_2.options.GraphRoute

@Composable
fun NavGraph(navController: NavHostController) {
    val sharedViewModel: SharedViewModel = viewModel(factory = MyViewModelProvider.factory)
    val sharedState by sharedViewModel.state.collectAsState()
    val onSharedEvent = sharedViewModel::onEvent

    NavHost(
        navController = navController,
        startDestination = GraphRoute.AuthGraph.name,
        route = GraphRoute.RootGraph.name
    ) {
        authNavGraph(
            navController = navController,
            sharedState = sharedState,
            onSharedEvent = onSharedEvent
        )
        composable(GraphRoute.HomeGraph.name) {
            HomeScreen(
                sharedState = sharedState,
                onSharedEvent = onSharedEvent
            )
        }
    }
}
