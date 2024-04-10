package com.example.pizza_pro_2.domain.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class SignUpViewModel(
    private val validateName: ValidateName = ValidateName(),
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword()
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpFormState())
    val state = _state.asStateFlow()

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

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
        val nameResult = validateName.execute(name = _state.value.name)
        val emailResult = validateEmail.execute(email = _state.value.email)
        val passwordResult = validatePassword.execute(password = _state.value.password)
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
