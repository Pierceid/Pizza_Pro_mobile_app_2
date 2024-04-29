package com.example.pizza_pro_2.domain.account

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.options.Gender
import com.example.pizza_pro_2.ui.theme.Slate

@Stable
data class AccountState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val gender: Gender = Gender.OTHER,
    val imageId: Int = R.drawable.profile_other,
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
    val dialogTitleId: Int = R.string.empty,
    val dialogTextId: Int = R.string.empty,
    val toastMessageId: Int = R.string.empty,
    val dialogEvent: AccountEvent? = null,
    val dialogColor: Color = Slate,
    val users: List<User> = emptyList()
)