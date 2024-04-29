package com.example.pizza_pro_2.domain.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.database.MyRepository
import com.example.pizza_pro_2.domain.ValidationEvent
import com.example.pizza_pro_2.options.Gender
import com.example.pizza_pro_2.ui.theme.Maroon
import com.example.pizza_pro_2.ui.theme.Teal
import com.example.pizza_pro_2.use_cases.ValidateEmail
import com.example.pizza_pro_2.use_cases.ValidateName
import com.example.pizza_pro_2.use_cases.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class AccountViewModel(
    private val myRepository: MyRepository,
    private val validateName: ValidateName = ValidateName(),
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword()
) : ViewModel() {
    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            myRepository.currentUser.firstOrNull()?.let { user ->
                _state.update {
                    it.copy(
                        name = user.name,
                        email = user.email,
                        password = user.password,
                        gender = user.gender,
                        imageId = getImageId(user.gender)
                    )
                }
            }

            myRepository.allUsers.collect { users ->
                _state.update {
                    it.copy(users = users)
                }
            }
        }
    }

    fun onEvent(event: AccountEvent) {
        viewModelScope.launch {
            when (event) {
                is AccountEvent.NameChanged -> {
                    _state.update {
                        it.copy(name = event.name)
                    }
                }

                is AccountEvent.EmailChanged -> {
                    _state.update {
                        it.copy(email = event.email)
                    }
                }

                is AccountEvent.PasswordChanged -> {
                    _state.update {
                        it.copy(password = event.password)
                    }
                }

                is AccountEvent.PasswordVisibilityChanged -> {
                    _state.update {
                        it.copy(isPasswordVisible = event.isVisible)
                    }
                }

                is AccountEvent.GenderChanged -> {
                    _state.update {
                        it.copy(
                            gender = event.gender, imageId = getImageId(event.gender)
                        )
                    }
                }

                is AccountEvent.NameEdited -> {
                    _state.update {
                        it.copy(
                            isNameEdited = true,
                            isEmailEdited = false,
                            isPasswordEdited = false,
                            isGenderEdited = false
                        )
                    }
                }

                is AccountEvent.EmailEdited -> {
                    _state.update {
                        it.copy(
                            isNameEdited = false,
                            isEmailEdited = true,
                            isPasswordEdited = false,
                            isGenderEdited = false
                        )
                    }
                }

                is AccountEvent.PasswordEdited -> {
                    _state.update {
                        it.copy(
                            isNameEdited = false,
                            isEmailEdited = false,
                            isPasswordEdited = true,
                            isGenderEdited = false
                        )
                    }
                }

                is AccountEvent.GenderEdited -> {
                    _state.update {
                        it.copy(
                            isNameEdited = false,
                            isEmailEdited = false,
                            isPasswordEdited = false,
                            isGenderEdited = event.isEdited
                        )
                    }
                }

                is AccountEvent.DialogVisibilityChanged -> {
                    _state.update {
                        it.copy(isDialogVisible = event.isVisible)
                    }
                }

                is AccountEvent.OptionChanged -> {
                    _state.update {
                        it.copy(buttonOption = event.option)
                    }
                    when (event.option) {
                        0 -> {
                            _state.update {
                                it.copy(
                                    dialogTitleId = R.string.update_account,
                                    dialogTextId = R.string.are_you_sure_you_want_to_save_changes,
                                    toastMessageId = R.string.account_updated_successfully,
                                    dialogEvent = AccountEvent.UpdateAccount,
                                    dialogColor = Teal
                                )
                            }
                        }

                        1 -> {
                            _state.update {
                                it.copy(
                                    isDialogVisible = true,
                                    dialogTitleId = R.string.delete_account,
                                    dialogTextId = R.string.are_you_certain_you_want_to_proceed_with_deleting_your_account,
                                    dialogEvent = AccountEvent.DeleteAccount,
                                    dialogColor = Maroon
                                )
                            }
                        }

                        2 -> {
                            _state.update {
                                it.copy(
                                    isDialogVisible = true,
                                    dialogTitleId = R.string.sign_out,
                                    dialogTextId = R.string.are_you_certain_you_want_to_sign_out_of_your_account,
                                    dialogEvent = AccountEvent.LogOut,
                                    dialogColor = Maroon
                                )
                            }
                        }

                        else -> {}
                    }
                }

                is AccountEvent.SubmitForm -> {
                    submitData(type = event.type)
                }

                is AccountEvent.UpdateAccount -> {
                    updateAccount()
                }

                is AccountEvent.DeleteAccount -> {
                    myRepository.currentUser.firstOrNull()?.let {
                        myRepository.deleteUser(it)
                    }
                    delay(300)
                    exitProcess(0)
                }

                is AccountEvent.LogOut -> {
                    exitProcess(0)
                }
            }
        }
    }

    private fun submitData(type: Int) {
        viewModelScope.launch {
            val result = validateUpdateAccount(type)

            if (result) {
                validationChannel.send(ValidationEvent.Success)
            }
        }
    }

    private suspend fun validateUpdateAccount(type: Int): Boolean {
        val user = myRepository.currentUser.firstOrNull() ?: return false

        val metNameCondition = user.name == _state.value.name ||
                !_state.value.users.any { it.name == _state.value.name }
        val metEmailCondition = user.email == _state.value.email ||
                !_state.value.users.any { it.email == _state.value.email }
        val metPasswordCondition = user.password == _state.value.password ||
                _state.value.password.any { it.isDigit() } &&
                _state.value.password.any { it.isLetter() }

        val nameResult = validateName.execute(_state.value.name, metNameCondition)
        val emailResult = validateEmail.execute(_state.value.email, metEmailCondition, type)
        val passwordResult =
            validatePassword.execute(_state.value.password, metPasswordCondition, type)
        val hasError = listOf(nameResult, emailResult, passwordResult).any { !it.successful }

        if (hasError) {
            _state.update {
                it.copy(
                    nameErrorId = nameResult.errorMessageId,
                    emailErrorId = emailResult.errorMessageId,
                    passwordErrorId = passwordResult.errorMessageId
                )
            }
            return false
        }

        refresh()

        return true
    }

    private fun updateAccount() {
        viewModelScope.launch {
            myRepository.currentUser.firstOrNull()?.let {
                myRepository.updateUser(
                    it.copy(
                        name = _state.value.name,
                        email = _state.value.email,
                        password = _state.value.password,
                        gender = _state.value.gender
                    )
                )
                myRepository.setCurrentUser(id = it.id)
            }
        }
    }

    private fun refresh() {
        _state.update {
            it.copy(
                nameErrorId = null,
                emailErrorId = null,
                passwordErrorId = null,
                isNameEdited = false,
                isEmailEdited = false,
                isPasswordEdited = false,
                isGenderEdited = false
            )
        }
    }

    private fun getImageId(gender: Gender): Int {
        return when (gender) {
            Gender.OTHER -> R.drawable.profile_other
            Gender.MALE -> R.drawable.profile_male
            Gender.FEMALE -> R.drawable.profile_female
        }
    }
}
