package com.example.pizza_pro_2.domain.sign_up

import com.example.pizza_pro_2.options.Gender

sealed class SignUpFormEvent {
    data class NameChanged(val name: String): SignUpFormEvent()
    data class EmailChanged(val email: String): SignUpFormEvent()
    data class PasswordChanged(val password: String): SignUpFormEvent()
    data class OnPasswordVisibilityChanged(val isVisible: Boolean): SignUpFormEvent()
    data class GenderChanged(val gender: Gender): SignUpFormEvent()
    data object Submit: SignUpFormEvent()
}
