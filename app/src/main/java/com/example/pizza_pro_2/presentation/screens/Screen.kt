package com.example.pizza_pro_2.presentation.screens

import com.example.pizza_pro_2.options.ScreenRoute

sealed class Screen(val route: String) {

    data object Intro : Screen(ScreenRoute.Intro.name)
    data object SignUp : Screen(ScreenRoute.SignUp.name)
    data object SignIn : Screen(ScreenRoute.SignIn.name)
    data object Shop : Screen(ScreenRoute.Shop.name)
    data object Cart : Screen(ScreenRoute.Cart.name)
    data object Feedback : Screen(ScreenRoute.Feedback.name)
    data object Settings : Screen(ScreenRoute.Settings.name)
    data object Account : Screen(ScreenRoute.Account.name)
    data object History : Screen(ScreenRoute.History.name)
    data object AboutApp : Screen(ScreenRoute.AboutApp.name)
}
