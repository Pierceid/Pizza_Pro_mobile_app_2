package com.example.pizza_pro_2.domain.account

import com.example.pizza_pro_2.options.Gender

sealed class AccountEvent {
    data class NameChanged(val name: String): AccountEvent()
    data class EmailChanged(val email: String): AccountEvent()
    data class PasswordChanged(val password: String): AccountEvent()
    data class PasswordVisibilityChanged(val isVisible: Boolean): AccountEvent()
    data class GenderChanged(val gender: Gender): AccountEvent()
    data class NameEdited(val isEdited: Boolean): AccountEvent()
    data class EmailEdited(val isEdited: Boolean): AccountEvent()
    data class PasswordEdited(val isEdited: Boolean): AccountEvent()
    data class GenderEdited(val isEdited: Boolean): AccountEvent()
    data class DialogVisibilityChanged(val isVisible: Boolean) : AccountEvent()
    data class OptionChanged(val option: Int): AccountEvent()
    data class SubmitForm(val type: Int): AccountEvent()
    data object UpdateAccount : AccountEvent()
    data object DeleteAccount: AccountEvent()
    data object LogOut: AccountEvent()
}
