package com.example.pizza_pro_2.domain.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.database.MyRepository
import com.example.pizza_pro_2.domain.ValidationEvent
import com.example.pizza_pro_2.use_cases.ValidateEmail
import com.example.pizza_pro_2.use_cases.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val myRepository: MyRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SignInFormState())
    val state = _state

    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            myRepository.getAllUsers().collect { users ->
                _state.value = _state.value.copy(users = users)
            }
        }
    }

    fun onEvent(event: SignInFormEvent) {
        viewModelScope.launch {
            when (event) {
                is SignInFormEvent.EmailChanged -> {
                    _state.update { currentState ->
                        currentState.copy(email = event.email)
                    }
                }

                is SignInFormEvent.PasswordChanged -> {
                    _state.update { currentState ->
                        currentState.copy(password = event.password)
                    }
                }

                is SignInFormEvent.OnPasswordVisibilityChanged -> {
                    _state.update { currentState ->
                        currentState.copy(passwordVisible = event.isVisible)
                    }
                }

                is SignInFormEvent.Submit -> {
                    submitData()
                }
            }
        }
    }

    private fun submitData() {
        val user = _state.value.users.firstOrNull { it.email == _state.value.email }
        val metEmailCondition = _state.value.users.any { it.email == _state.value.email }
        val emailResult = validateEmail.execute(_state.value.email, metEmailCondition, false)
        val metPasswordCondition = user != null && user.password == _state.value.password
        val passwordResult = validatePassword.execute(_state.value.password, metPasswordCondition, false)
        val hasError = listOf(emailResult, passwordResult).any { !it.successful }

        if (hasError) {
            _state.update { currentState ->
                currentState.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage
                )
            }
            return
        }

        if (user != null) {
            _state.update { currentState ->
                currentState.copy(name = user.name, gender = user.gender)
            }
        }

        viewModelScope.launch {
            validationChannel.send(ValidationEvent.Success)
        }
    }
}
