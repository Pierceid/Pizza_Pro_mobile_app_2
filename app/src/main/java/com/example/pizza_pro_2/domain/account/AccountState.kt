package com.example.pizza_pro_2.domain.account

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.options.Gender

@Stable
data class AccountState(
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
    val buttonOption: Int = -1,
    val dialogTitleId: Int = -1,
    val dialogTextId: Int = -1,
    val dialogEvent: AccountEvent? = null,
    val dialogColor: Color? = null,
    val users: List<User> = emptyList()
)