package com.example.pizza_pro_2.navigation

const val ID = "id"
const val NAME = "name"
const val EMAIL = "email"
const val PASSWORD = "password"
const val GENDER = "gender"
const val LOCATION = "location"

const val ROOT_GRAPH_ROUTE = "root"
const val AUTH_GRAPH_ROUTE = "auth"
const val HOME_GRAPH_ROUTE = "home"

sealed class Screen (val route: String) {
    data object Intro : Screen(route = "intro_screen")
    data object Register : Screen(route = "register_screen")
    data object Login : Screen(route = "login_screen")
    data object Shop : Screen(route = "shop_screen")
    data object Cart : Screen(route = "cart_screen")
    data object Feedback : Screen(route = "feedback_screen")
    data object Profile : Screen(route = "profile_screen?name={name}&email={email}") {
        fun getArguments(
            name: String = "Pierceid",
            email: String = "e@e.e"
        ): String {
            return "profile_screen?name=$name&email=$name"
        }
    }
}