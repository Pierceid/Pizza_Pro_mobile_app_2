package com.example.pizza_pro_2.navigation

import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.options.GraphRoute
import com.example.pizza_pro_2.options.ScreenRoute
import com.example.pizza_pro_2.presentation.screens.Screen

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val selectedIconId: Int,
    val unselectedIconId: Int
) {
    data object Shop: BottomBarScreen(
        route = Screen.Shop.route,
        title = ScreenRoute.Shop.name,
        selectedIconId = R.drawable.filled_shop_24,
        unselectedIconId = R.drawable.outlined_shop_24
    )
    data object Cart: BottomBarScreen(
        route = Screen.Cart.route,
        title = ScreenRoute.Cart.name,
        selectedIconId = R.drawable.filled_cart_24,
        unselectedIconId = R.drawable.outlined_cart_24
    )
    data object Feedback: BottomBarScreen(
        route = Screen.Feedback.route,
        title = ScreenRoute.Feedback.name,
        selectedIconId = R.drawable.filled_feedback_24,
        unselectedIconId = R.drawable.outlined_feedback_24
    )

    data object Settings: BottomBarScreen(
        route = GraphRoute.SettingsGraph.name,
        title = ScreenRoute.Settings.name,
        selectedIconId = R.drawable.filled_settings_24,
        unselectedIconId = R.drawable.outlined_settings_24
    )
}
