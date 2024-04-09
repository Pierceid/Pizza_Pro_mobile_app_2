package com.example.pizza_pro_2.presentation.screens

const val ROOT_GRAPH_ROUTE = "root"
const val AUTH_GRAPH_ROUTE = "auth"
const val HOME_GRAPH_ROUTE = "home"
const val SETTINGS_GRAPH_ROUTE = "settings"

sealed class Screen (val route: String) {
    data object Intro : Screen(route = "intro_screen")
    data object SignUp : Screen(route = "sign_up_screen")
    data object SignIn : Screen(route = "sign_in_screen")
    data object Shop : Screen(route = "shop_screen")
    data object Cart : Screen(route = "cart_screen")
    data object Feedback : Screen(route = "feedback_screen")
    data object Settings : Screen(route = "settings_screen")
    data object Account : Screen(route = "account_screen")
    data object History : Screen(route = "history_screen")
    data object AboutApp : Screen(route = "about_aApp_screen")
}
