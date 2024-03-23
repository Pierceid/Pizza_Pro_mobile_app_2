package com.example.pizza_pro_2.domain

import com.example.pizza_pro_2.options.Gender

sealed class SignInFormEvent {
    data class EmailChanged(val email: String): SignInFormEvent()
    data class PasswordChanged(val password: String): SignInFormEvent()
    data class LocationChanged(val location: String): SignInFormEvent()
    data class GenderChanged(val gender: Gender): SignInFormEvent()
    data object Submit: SignInFormEvent()
}
