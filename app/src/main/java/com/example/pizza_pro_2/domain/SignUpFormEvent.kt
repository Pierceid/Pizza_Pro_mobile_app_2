package com.example.pizza_pro_2.domain

sealed class SignUpFormEvent {
    data class NameChanged(val name: String): SignUpFormEvent()
    data class EmailChanged(val email: String): SignUpFormEvent()
    data class PasswordChanged(val password: String): SignUpFormEvent()
    data class LocationChanged(val location: String): SignUpFormEvent()

    data object Submit: SignUpFormEvent()
}