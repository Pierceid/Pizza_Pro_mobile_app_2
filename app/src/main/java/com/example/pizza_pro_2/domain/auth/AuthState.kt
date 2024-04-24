package com.example.pizza_pro_2.domain.auth

import androidx.compose.runtime.Stable
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.options.Gender

@Stable
data class AuthState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val gender: Gender = Gender.OTHER,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isPasswordVisible: Boolean = false,
    val isEmailEdited: Boolean = false,
    val isNameEdited: Boolean = false,
    val isPasswordEdited: Boolean = false,
    val isGenderEdited: Boolean = false,
    val users: List<User> = emptyList()
)