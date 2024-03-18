package com.example.pizza_pro_2.presentation

sealed class SignInFormEvent {
    data class EmailChanged(val email: String): SignInFormEvent()
    data class PasswordChanged(val password: String): SignInFormEvent()
    data class LocationChanged(val location: String): SignInFormEvent()

    data object Submit: SignInFormEvent()
}