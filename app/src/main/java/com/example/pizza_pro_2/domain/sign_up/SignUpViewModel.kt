package com.example.pizza_pro_2.domain.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.database.MyRepository
import com.example.pizza_pro_2.domain.ValidationEvent
import com.example.pizza_pro_2.use_cases.ValidateEmail
import com.example.pizza_pro_2.use_cases.ValidateName
import com.example.pizza_pro_2.use_cases.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val validateName: ValidateName = ValidateName(),
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val myRepository: MyRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpFormState())
    val state = _state

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            myRepository.getAllUsers().collect { users ->
                _state.update { currentState ->
                    currentState.copy(users = users)
                }
            }
        }
    }

    fun onEvent(event: SignUpFormEvent) {
        viewModelScope.launch {
            when (event) {
                is SignUpFormEvent.NameChanged -> {
                    _state.update { currentState ->
                        currentState.copy(name = event.name)
                    }
                }

                is SignUpFormEvent.EmailChanged -> {
                    _state.update { currentState ->
                        currentState.copy(email = event.email)
                    }
                }

                is SignUpFormEvent.PasswordChanged -> {
                    _state.update { currentState ->
                        currentState.copy(password = event.password)
                    }
                }

                is SignUpFormEvent.OnPasswordVisibilityChanged -> {
                    _state.update { currentState ->
                        currentState.copy(passwordVisible = event.isVisible)
                    }
                }

                is SignUpFormEvent.GenderChanged -> {
                    _state.update { currentState ->
                        currentState.copy(gender = event.gender)
                    }
                }

                is SignUpFormEvent.Submit -> {
                    submitData()
                }
            }
        }
    }

    private fun submitData() {
        val metNameCondition = !_state.value.users.any { it.name == _state.value.name }
        val metEmailCondition = !_state.value.users.any { it.email == _state.value.email }
        val metPasswordCondition =
            _state.value.password.any { it.isDigit() } && _state.value.password.any { it.isLetter() }
        val nameResult = validateName.execute(_state.value.name, metNameCondition)
        val emailResult = validateEmail.execute(_state.value.email, metEmailCondition, true)
        val passwordResult =
            validatePassword.execute(_state.value.password, metPasswordCondition, true)
        val hasError = listOf(nameResult, emailResult, passwordResult).any { !it.successful }

        if (hasError) {
            _state.update { currentState ->
                currentState.copy(
                    nameError = nameResult.errorMessage,
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage
                )
            }
            return
        }

        viewModelScope.launch {
            validationChannel.send(ValidationEvent.Success)
        }
    }
}
