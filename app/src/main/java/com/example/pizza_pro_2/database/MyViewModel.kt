package com.example.pizza_pro_2.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.database.events.UserEvent
import com.example.pizza_pro_2.database.states.AuthState
import com.example.pizza_pro_2.domain.ValidationEvent
import com.example.pizza_pro_2.use_cases.ValidateEmail
import com.example.pizza_pro_2.use_cases.ValidateName
import com.example.pizza_pro_2.use_cases.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyViewModel(
    private val validateName: ValidateName = ValidateName(),
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val dao: MyDao
) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    private val validationChannel = Channel<ValidationEvent>()
    val validationEvents = validationChannel.receiveAsFlow()
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), AuthState())
    fun onEvent(event: UserEvent) {
        viewModelScope.launch {
            when (event) {
                is UserEvent.SetName -> {
                    _state.update {
                        it.copy(name = event.name)
                    }
                }

                is UserEvent.SetEmail -> {
                    _state.update {
                        it.copy(email = event.email)
                    }
                }

                is UserEvent.SetPassword -> {
                    _state.update {
                        it.copy(password = event.password)
                    }
                }

                is UserEvent.SetGender -> {
                    _state.update {
                        it.copy(gender = event.gender)
                    }
                }

                UserEvent.Submit -> {
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
            _state.update {
                it.copy(
                    nameError = nameResult.errorMessage,
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage
                )
            }
            return
        }

        viewModelScope.launch {
            val user = User(
                name = _state.value.name,
                email = _state.value.email,
                password = _state.value.password,
                gender = _state.value.gender
            )

            dao.insertUser(user)

            validationChannel.send(ValidationEvent.Success)
        }
    }
}
