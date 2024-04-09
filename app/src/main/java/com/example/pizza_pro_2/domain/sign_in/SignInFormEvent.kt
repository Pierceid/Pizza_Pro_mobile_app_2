package com.example.pizza_pro_2.domain.sign_in

sealed class SignInFormEvent {
    data class EmailChanged(val email: String): SignInFormEvent()
    data class PasswordChanged(val password: String): SignInFormEvent()
    data class OnPasswordVisibilityChanged(val isVisible: Boolean): SignInFormEvent()
    data object Submit: SignInFormEvent()
}
