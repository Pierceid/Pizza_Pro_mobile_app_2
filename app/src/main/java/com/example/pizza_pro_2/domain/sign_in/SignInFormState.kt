package com.example.pizza_pro_2.domain.sign_in

import androidx.compose.runtime.Stable

@Stable
data class SignInFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val passwordVisible: Boolean = false
)