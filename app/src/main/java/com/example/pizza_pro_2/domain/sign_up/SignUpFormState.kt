package com.example.pizza_pro_2.domain.sign_up

import androidx.compose.runtime.Stable
import com.example.pizza_pro_2.options.Gender

@Stable
data class SignUpFormState(
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val location: String = "",
    val locationError: String? = null,
    val gender: Gender = Gender.OTHER
)