package com.example.pizza_pro_2.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.SignInFormEvent
import com.example.pizza_pro_2.domain.ValidationEvent
import com.example.pizza_pro_2.options.Gender
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.ErrorText
import com.example.pizza_pro_2.presentation.components.FooterText
import com.example.pizza_pro_2.presentation.components.HeaderText
import com.example.pizza_pro_2.presentation.components.InputTextField
import com.example.pizza_pro_2.presentation.components.RadioGroup
import com.example.pizza_pro_2.view_models.SharedViewModel
import com.example.pizza_pro_2.view_models.SignInViewModel

@Composable
fun SignInScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val viewModel = viewModel<SignInViewModel>()
    val state = viewModel.state
    val context = LocalContext.current
    val toastMessage = stringResource(id = R.string.signed_in_successfully)

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
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
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
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
