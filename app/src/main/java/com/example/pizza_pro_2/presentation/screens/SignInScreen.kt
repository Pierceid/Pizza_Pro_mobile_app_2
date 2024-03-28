package com.example.pizza_pro_2.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.domain.ValidationEvent
import com.example.pizza_pro_2.domain.shared.SharedFormEvent
import com.example.pizza_pro_2.domain.shared.SharedViewModel
import com.example.pizza_pro_2.domain.sign_in.SignInFormEvent
import com.example.pizza_pro_2.domain.sign_in.SignInFormState
import com.example.pizza_pro_2.domain.sign_in.SignInViewModel
import com.example.pizza_pro_2.options.Gender
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.ErrorText
import com.example.pizza_pro_2.presentation.components.FooterText
import com.example.pizza_pro_2.presentation.components.HeaderText
import com.example.pizza_pro_2.presentation.components.InputTextField
import com.example.pizza_pro_2.presentation.components.RadioGroup

@Composable
fun SignInScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val viewModel = viewModel<SignInViewModel>()
    val state = viewModel.state
    val context = LocalContext.current
    val toastMessage = stringResource(id = R.string.signed_in_successfully)
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    setCurrentUser(state, sharedViewModel)

                    navController.navigate(HOME_GRAPH_ROUTE) {
                        popUpTo(Screen.Intro.route) {
                            inclusive = true
                        }
                    }

                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    DefaultColumn {
        Column(
            modifier = Modifier.width(480.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderText(text = stringResource(id = R.string.sign_in))

            Spacer(modifier = Modifier.height(16.dp))

            InputTextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(SignInFormEvent.EmailChanged(it))
                },
                label = stringResource(id = R.string.email),
                isError = state.emailError != null,
                leadingIcon = Icons.Default.Email,
                trailingIcon = Icons.Default.Clear,
                onTrailingIconClick = {
                    viewModel.onEvent(SignInFormEvent.EmailChanged(""))
                },
                keyboardType = KeyboardType.Email
            )

            if (state.emailError != null) {
                ErrorText(message = state.emailError)
            }

            Spacer(modifier = Modifier.height(8.dp))

            InputTextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(SignInFormEvent.PasswordChanged(it))
                },
                label = stringResource(id = R.string.password),
                isError = state.passwordError != null,
                leadingIcon = Icons.Default.Lock,
                trailingIcon = if (passwordVisible) Icons.Filled.Info else Icons.Outlined.Info,
                onTrailingIconClick = { passwordVisible = !passwordVisible },
                keyboardType = KeyboardType.Password,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )

            if (state.passwordError != null) {
                ErrorText(message = state.passwordError)
            }

            Spacer(modifier = Modifier.height(8.dp))

            InputTextField(
                value = state.location,
                onValueChange = {
                    viewModel.onEvent(SignInFormEvent.LocationChanged(it))
                },
                label = stringResource(id = R.string.location),
                isError = state.locationError != null,
                leadingIcon = Icons.Default.LocationOn,
                trailingIcon = Icons.Default.Clear,
                onTrailingIconClick = {
                    viewModel.onEvent(SignInFormEvent.LocationChanged(""))
                }
            )

            if (state.locationError != null) {
                ErrorText(message = state.locationError)
            }

            Spacer(modifier = Modifier.height(8.dp))

            RadioGroup(
                selected = state.gender,
                onSelectionChange = {
                    viewModel.onEvent(SignInFormEvent.GenderChanged(it))
                },
                options = listOf(Gender.OTHER, Gender.MALE, Gender.FEMALE),
                modifier = Modifier.padding(end = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            ActionButton(
                text = stringResource(id = R.string.sign_in),
                onClick = {
                    viewModel.onEvent(SignInFormEvent.Submit)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            FooterText(
                text = stringResource(id = R.string.don_t_have_an_account),
                hyperText = stringResource(id = R.string.sign_up),
                onClick = {
                    navController.navigate(Screen.SignUp.route) {
                        popUpTo(Screen.SignIn.route) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

private fun setCurrentUser(state: SignInFormState, sharedViewModel: SharedViewModel) {
    val user = User(
        name = "Jozef Potato",
        email = state.email,
        password = state.password,
        location = state.location,
        gender = state.gender
    )

    sharedViewModel.onEvent(SharedFormEvent.CurrentUserChanged(user))
}