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
import kotlinx.coroutines.flow.collectLatest
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
    var state = _state.asStateFlow()

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            myRepository.getAllUsers().collectLatest { users ->
                _state.update { currentState ->
                    currentState.copy(users = users)
                }
            }
        }
    }

    fun onEvent(event: AuthEvent) {
        viewModelScope.launch {
            when (event) {
                is AuthEvent.NameChanged -> {
                    _state.update { currentState ->
                        currentState.copy(name = event.name)
                    }
                }

                is AuthEvent.EmailChanged -> {
                    _state.update { currentState ->
                        currentState.copy(email = event.email)
                    }
                }

                is AuthEvent.PasswordChanged -> {
                    _state.update { currentState ->
                        currentState.copy(password = event.password)
                    }
                }

                is AuthEvent.PasswordVisibilityChanged -> {
                    _state.update { currentState ->
                        currentState.copy(isPasswordVisible = event.isVisible)
                    }
                }

                is AuthEvent.GenderChanged -> {
                    _state.update { currentState ->
                        currentState.copy(gender = event.gender)
                    }
                }

                is AuthEvent.NameEdited -> {
                    _state.update { currentState ->
                        currentState.copy(isNameEdited = event.isEdited)
                    }
                }

                is AuthEvent.EmailEdited -> {
                    _state.update { currentState ->
                        currentState.copy(isEmailEdited = event.isEdited)
                    }
                }

                is AuthEvent.PasswordEdited -> {
                    _state.update { currentState ->
                        currentState.copy(isPasswordEdited = event.isEdited)
                    }
                }

                is AuthEvent.GenderEdited -> {
                    _state.update { currentState ->
                        currentState.copy(isGenderEdited = event.isEdited)
                    }
                }

                is AuthEvent.Submit -> {
                    submitData(type = event.type, currentUser = event.currentUser)
                }
            }
        }
    }

    private fun submitData(type: Int, currentUser: User?) {
        val result = when (type) {
            0 -> validateSignUp(type)
            1 -> validateSignIn(type)
            2 -> validateUpdateAccount(type, currentUser)
            else -> false
        }

        if (result) {
            viewModelScope.launch {
                validationChannel.send(ValidationEvent.Success)
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
            _state.update { currentState ->
                currentState.copy(
                    nameErrorId = nameResult.errorMessageId,
                    emailErrorId = emailResult.errorMessageId,
                    passwordErrorId = passwordResult.errorMessageId
                )
            }
            return false
        }

        return true
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
            _state.update { currentState ->
                currentState.copy(
                    emailErrorId = emailResult.errorMessageId,
                    passwordErrorId = passwordResult.errorMessageId
                )
            }
            return false
        }

        return true
    }

    private fun validateUpdateAccount(type: Int, currentUser: User?): Boolean {
        if (currentUser != null) {
            val metNameCondition = currentUser.name == _state.value.name ||
                    !_state.value.users.any { it.name == _state.value.name && it != currentUser }
            val metEmailCondition = currentUser.email == _state.value.email ||
                    !_state.value.users.any { it.email == _state.value.email && it != currentUser }
            val metPasswordCondition = currentUser.password == _state.value.password ||
                    _state.value.password.any { it.isDigit() } && _state.value.password.any { it.isLetter() }

            val nameResult = validateName.execute(_state.value.name, metNameCondition)
            val emailResult =
                validateEmail.execute(_state.value.email, metEmailCondition, type)
            val passwordResult =
                validatePassword.execute(_state.value.password, metPasswordCondition, type)
            val hasError =
                listOf(nameResult, emailResult, passwordResult).any { !it.successful }

            if (hasError) {
                _state.update { currentState ->
                    currentState.copy(
                        nameErrorId = nameResult.errorMessageId,
                        emailErrorId = emailResult.errorMessageId,
                        passwordErrorId = passwordResult.errorMessageId
                    )
                }
                return false
            }

            _state.update { currentState ->
                currentState.copy(
                    name = _state.value.name,
                    email = _state.value.email,
                    password = _state.value.password,
                    gender = _state.value.gender,
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

        return true
    }

    fun refresh(user: User? = null) {
        if (user != null) {
            _state.update { currentState ->
                currentState.copy(
                    name = user.name,
                    email = user.email,
                    password = user.password,
                    gender = user.gender
                )
            }
        } else {
            viewModelScope.launch {
                myRepository.getUser(_state.value.name, _state.value.email)
                    .collectLatest { currentUser ->
                        currentUser?.let {
                            _state.update { currentState ->
                                currentState.copy(
                                    name = currentUser.name,
                                    email = currentUser.email,
                                    password = currentUser.password,
                                    gender = currentUser.gender
                                )
                            }
                        }
                    }
            }
        }
    }
}
