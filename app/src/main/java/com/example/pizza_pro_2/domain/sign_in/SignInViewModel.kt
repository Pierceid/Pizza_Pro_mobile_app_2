package com.example.pizza_pro_2.domain.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.domain.ValidationEvent
import com.example.pizza_pro_2.use_cases.ValidateEmail
import com.example.pizza_pro_2.use_cases.ValidateLocation
import com.example.pizza_pro_2.use_cases.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateLocation: ValidateLocation = ValidateLocation()
) : ViewModel() {
    var state: SignInFormState by mutableStateOf(SignInFormState())
    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()

    fun onEvent(event: SignInFormEvent) {
        viewModelScope.launch {
            when (event) {
                is SignInFormEvent.EmailChanged -> {
                    state = state.copy(email = event.email)
                }

                is SignInFormEvent.PasswordChanged -> {
                    state = state.copy(password = event.password)
                }

                is SignInFormEvent.LocationChanged -> {
                    state = state.copy(location = event.location)
                }

                is SignInFormEvent.GenderChanged -> {
                    state = state.copy(gender = event.gender)
                }

                is SignInFormEvent.Submit -> {
                    submitData()
                }
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(email = state.email)
        val passwordResult = validatePassword.execute(password = state.password)
        val locationResult = validateLocation.execute(location = state.location)

        val hasError = listOf(
            emailResult,
            passwordResult,
            locationResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
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
}
