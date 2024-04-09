package com.example.pizza_pro_2.database.events

import com.example.pizza_pro_2.options.Gender

sealed interface UserEvent {
    data class SetName(val name: String): UserEvent
    data class SetEmail(val email: String): UserEvent
    data class SetPassword(val password: String): UserEvent
    data class SetGender(val gender: Gender): UserEvent
    data object Submit: UserEvent
}