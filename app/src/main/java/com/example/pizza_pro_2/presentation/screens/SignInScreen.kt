package com.example.pizza_pro_2.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.database.MyDao
import com.example.pizza_pro_2.domain.ValidationEvent
import com.example.pizza_pro_2.domain.shared.SharedFormEvent
import com.example.pizza_pro_2.domain.shared.SharedFormState
import com.example.pizza_pro_2.domain.sign_in.SignInFormEvent
import com.example.pizza_pro_2.domain.sign_in.SignInViewModel
import com.example.pizza_pro_2.options.GraphRoute
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.ErrorText
import com.example.pizza_pro_2.presentation.components.FooterText
import com.example.pizza_pro_2.presentation.components.HeaderText
import com.example.pizza_pro_2.presentation.components.InputTextField
import com.example.pizza_pro_2.ui.theme.White

@Composable
fun SignInScreen(
    navController: NavHostController,
    sharedState: SharedFormState,
    onSharedEvent: (SharedFormEvent) -> Unit,
    myDao: MyDao
) {
    val viewModel = viewModel<SignInViewModel>()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val toastMessage = stringResource(id = R.string.signed_in_successfully)

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate(GraphRoute.HomeGraph.name) {
                        popUpTo(GraphRoute.AuthGraph.name) {
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

            state.emailError?.let {
                ErrorText(message = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InputTextField(
                    modifier = Modifier.weight(1f),
                    value = state.password,
                    onValueChange = {
                        viewModel.onEvent(SignInFormEvent.PasswordChanged(it))
                    },
                    label = stringResource(id = R.string.password),
                    isError = state.passwordError != null,
                    leadingIcon = Icons.Default.Lock,
                    trailingIcon = Icons.Default.Clear,
                    onTrailingIconClick = {
                        viewModel.onEvent(SignInFormEvent.PasswordChanged(""))
                    },
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    visualTransformation = if (state.passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 4.dp)
                        .clickable {
                            viewModel.onEvent(SignInFormEvent.OnPasswordVisibilityChanged(!state.passwordVisible))
                        },
                    painter = painterResource(id = if (state.passwordVisible) R.drawable.visible_24 else R.drawable.hidden_24),
                    contentDescription = stringResource(id = R.string.visibility),
                    tint = White
                )
            }

            state.passwordError?.let {
                ErrorText(message = it)
            }

            Spacer(modifier = Modifier.height(24.dp))

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
