package com.example.pizza_pro_2.domain

import androidx.compose.runtime.Stable

@Stable
data class SignInFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val location: String = "",
    val locationError: String? = null
)