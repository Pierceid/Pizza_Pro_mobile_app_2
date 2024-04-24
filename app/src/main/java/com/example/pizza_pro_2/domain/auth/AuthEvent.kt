package com.example.pizza_pro_2.domain.auth

import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.options.Gender

sealed class AuthEvent {
    data class NameChanged(val name: String): AuthEvent()
    data class EmailChanged(val email: String): AuthEvent()
    data class PasswordChanged(val password: String): AuthEvent()
    data class PasswordVisibilityChanged(val isVisible: Boolean): AuthEvent()
    data class GenderChanged(val gender: Gender): AuthEvent()
    data class NameEdited(val isEdited: Boolean): AuthEvent()
    data class EmailEdited(val isEdited: Boolean): AuthEvent()
    data class PasswordEdited(val isEdited: Boolean): AuthEvent()
    data class GenderEdited(val isEdited: Boolean): AuthEvent()
    data class Submit(val type: Int, val currentUser: User? = null): AuthEvent()
}
