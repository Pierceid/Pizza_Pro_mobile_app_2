package com.example.pizza_pro_2.domain.auth

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.options.Gender

@Stable
data class AuthState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val gender: Gender = Gender.OTHER,
    @StringRes val nameErrorId: Int? = null,
    @StringRes val emailErrorId: Int? = null,
    @StringRes val passwordErrorId: Int? = null,
    val isPasswordVisible: Boolean = false,
    val isNameEdited: Boolean = false,
    val isEmailEdited: Boolean = false,
    val isPasswordEdited: Boolean = false,
    val isGenderEdited: Boolean = false,
    val isDialogVisible: Boolean = false,
    val users: List<User> = emptyList()
)