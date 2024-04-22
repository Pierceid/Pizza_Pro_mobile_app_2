package com.example.pizza_pro_2.domain.sign_in

import androidx.compose.runtime.Stable
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.options.Gender

@Stable
data class SignInFormState(
    val name: String = "",
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val passwordVisible: Boolean = false,
    val gender: Gender = Gender.OTHER,
    val users: List<User> = emptyList()
)