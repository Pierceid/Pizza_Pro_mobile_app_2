package com.example.pizza_pro_2.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.domain.ValidateEmail
import com.example.pizza_pro_2.domain.ValidateLocation
import com.example.pizza_pro_2.domain.ValidateName
import com.example.pizza_pro_2.domain.ValidatePassword
import com.example.pizza_pro_2.presentation.SignUpFormEvent
import com.example.pizza_pro_2.presentation.SignUpFormState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val validateName: ValidateName = ValidateName(),
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateLocation: ValidateLocation = ValidateLocation()
) : ViewModel() {
    var state: SignUpFormState by mutableStateOf(SignUpFormState())
    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    fun onEvent(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.NameChanged -> {
                state = state.copy(name = event.name)
            }

            is SignUpFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is SignUpFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is SignUpFormEvent.LocationChanged -> {
                state = state.copy(location = event.location)
            }

            is SignUpFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val nameResult = validateName.execute(name = state.name)
        val emailResult = validateEmail.execute(email = state.email)
        val passwordResult = validatePassword.execute(password = state.password)
        val locationResult = validateLocation.execute(location = state.location)

        val hasError = listOf(
            nameResult,
            emailResult,
            passwordResult,
            locationResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                nameError = nameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                locationError = locationResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            validationChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }
}
