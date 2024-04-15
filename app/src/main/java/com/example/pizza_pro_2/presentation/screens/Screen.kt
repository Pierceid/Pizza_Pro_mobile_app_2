package com.example.pizza_pro_2.presentation.screens

import com.example.pizza_pro_2.options.ScreenRoute

sealed class Screen(val route: String) {

    data object Intro : Screen(route = ScreenRoute.Intro.name)
    data object SignUp : Screen(route = ScreenRoute.SignUp.name)
    data object SignIn : Screen(route = ScreenRoute.SignIn.name)
    data object Shop : Screen(route = ScreenRoute.Shop.name)
    data object Cart : Screen(route = ScreenRoute.Cart.name)
    data object Feedback : Screen(route = ScreenRoute.Feedback.name)
    data object Settings : Screen(route = ScreenRoute.Settings.name)
    data object Account : Screen(route = ScreenRoute.Account.name)
    data object History : Screen(route = ScreenRoute.History.name)
    data object AboutApp : Screen(route = ScreenRoute.AboutApp.name)
}
