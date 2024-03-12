package com.example.pizza_pro_2.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Shop: BottomBarScreen(
        route = Screen.Shop.route,
        title = "Shop",
        icon = Icons.Default.Home
    )
    data object Cart: BottomBarScreen(
        route = Screen.Cart.route,
        title = "Cart",
        icon = Icons.Default.ShoppingCart
    )
    data object Feedback: BottomBarScreen(
        route = Screen.Feedback.route,
        title = "Feedback",
        icon = Icons.Default.Email
    )

    data object Profile: BottomBarScreen(
        route = Screen.Profile.route,
        title = "Profile",
        icon = Icons.Default.Person
    )
}

