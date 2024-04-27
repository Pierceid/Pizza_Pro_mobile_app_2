package com.example.pizza_pro_2.domain.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.database.MyRepository
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.domain.ValidationEvent
import com.example.pizza_pro_2.use_cases.ValidateEmail
import com.example.pizza_pro_2.use_cases.ValidateName
import com.example.pizza_pro_2.use_cases.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val myRepository: MyRepository,
    private val validateName: ValidateName = ValidateName(),
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword()
) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            myRepository.allUsers.collect { users ->
                _state.update {
                    it.copy(users = users)
                }
            }
        }
    }

    fun onEvent(event: AuthEvent) {
        viewModelScope.launch {
            when (event) {
                is AuthEvent.NameChanged -> {
                    _state.update {
                        it.copy(name = event.name)
                    }
                }

                is AuthEvent.EmailChanged -> {
                    _state.update {
                        it.copy(email = event.email)
                    }
                }

                is AuthEvent.PasswordChanged -> {
                    _state.update {
                        it.copy(password = event.password)
                    }
                }

                is AuthEvent.PasswordVisibilityChanged -> {
                    _state.update {
                        it.copy(isPasswordVisible = event.isVisible)
                    }
                }

                is AuthEvent.GenderChanged -> {
                    _state.update {
                        it.copy(gender = event.gender)
                    }
                }

                is AuthEvent.SubmitForm -> {
                    submitData(type = event.type)
                }
            }
        }
    }

    private fun submitData(type: Int) {
        viewModelScope.launch {
            val result = when (type) {
                0 -> validateSignUp(type)
                1 -> validateSignIn(type)
                else -> false
            }

            if (result) {
                validationChannel.send((ValidationEvent.Success))
            }
        }
    }

    private fun validateSignUp(type: Int): Boolean {
        val metNameCondition = !_state.value.users.any { it.name == _state.value.name }
        val metEmailCondition = !_state.value.users.any { it.email == _state.value.email }
        val metPasswordCondition =
            _state.value.password.any { it.isDigit() } && _state.value.password.any { it.isLetter() }

        val nameResult = validateName.execute(_state.value.name, metNameCondition)
        val emailResult = validateEmail.execute(_state.value.email, metEmailCondition, type)
        val passwordResult =
            validatePassword.execute(_state.value.password, metPasswordCondition, type)
        val hasError =
            listOf(nameResult, emailResult, passwordResult).any { !it.successful }

        if (hasError) {
            _state.update {
                it.copy(
                    nameErrorId = nameResult.errorMessageId,
                    emailErrorId = emailResult.errorMessageId,
                    passwordErrorId = passwordResult.errorMessageId
                )
            }
            return false
        } else {
            viewModelScope.launch {
                val newUser = User(
                    name = _state.value.name,
                    email = _state.value.email,
                    password = _state.value.password,
                    gender = _state.value.gender
                )
                myRepository.insertUser(newUser)
                myRepository.setCurrentUser(email = _state.value.email)
            }
            return true
        }
    }

    private fun validateSignIn(type: Int): Boolean {
        val user = _state.value.users.firstOrNull { it.email == _state.value.email }

        val metEmailCondition = _state.value.users.any { it.email == _state.value.email }
        val metPasswordCondition = user != null && user.password == _state.value.password

        val emailResult = validateEmail.execute(_state.value.email, metEmailCondition, type)
        val passwordResult =
            validatePassword.execute(_state.value.password, metPasswordCondition, type)
        val hasError = listOf(emailResult, passwordResult).any { !it.successful }

        if (hasError) {
            _state.update {
                it.copy(
                    emailErrorId = emailResult.errorMessageId,
                    passwordErrorId = passwordResult.errorMessageId
                )
            }
            return false
        }

        viewModelScope.launch {
            myRepository.setCurrentUser(email = _state.value.email)
        }

        return true
    }
}
