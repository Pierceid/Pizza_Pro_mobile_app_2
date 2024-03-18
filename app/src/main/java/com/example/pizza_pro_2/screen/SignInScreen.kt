package com.example.pizza_pro_2.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.component.ActionButton
import com.example.pizza_pro_2.component.AuthTextField
import com.example.pizza_pro_2.component.DefaultColumn
import com.example.pizza_pro_2.component.ErrorText
import com.example.pizza_pro_2.component.FooterText
import com.example.pizza_pro_2.component.HeaderText
import com.example.pizza_pro_2.navigation.HOME_GRAPH_ROUTE
import com.example.pizza_pro_2.navigation.Screen
import com.example.pizza_pro_2.presentation.SignInFormEvent
import com.example.pizza_pro_2.view_model.SignInViewModel

@Composable
fun SignInScreen(navController: NavController) {
    val viewModel = viewModel<SignInViewModel>()
    val state = viewModel.state
    val context = LocalContext.current
    val toastMessage = stringResource(id = R.string.signed_in_successfully)

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is SignInViewModel.ValidationEvent.Success -> {
                    navController.navigate(HOME_GRAPH_ROUTE) {
                        popUpTo(Screen.Intro.route) {
                            inclusive = true
                        }
                    }

                    Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    DefaultColumn {
        HeaderText(text = stringResource(id = R.string.sign_in))

        Spacer(modifier = Modifier.height(20.dp))

        AuthTextField(
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

        AuthTextField(
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

        AuthTextField(
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

        Spacer(modifier = Modifier.height(40.dp))

        ActionButton(
            text = stringResource(id = R.string.sign_in),
            onClick = { viewModel.onEvent(SignInFormEvent.Submit) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        FooterText(
            text = stringResource(id = R.string.do_not_have_an_account),
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
