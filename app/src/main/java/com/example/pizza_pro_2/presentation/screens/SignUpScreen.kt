package com.example.pizza_pro_2.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.MyViewModelProvider
import com.example.pizza_pro_2.domain.ValidationEvent
import com.example.pizza_pro_2.domain.auth.AuthEvent
import com.example.pizza_pro_2.domain.auth.AuthViewModel
import com.example.pizza_pro_2.options.Gender
import com.example.pizza_pro_2.options.GraphRoute
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.ErrorText
import com.example.pizza_pro_2.presentation.components.FooterText
import com.example.pizza_pro_2.presentation.components.HeaderText
import com.example.pizza_pro_2.presentation.components.InputTextField
import com.example.pizza_pro_2.presentation.components.RadioGroup
import com.example.pizza_pro_2.ui.theme.White

@Composable
fun SignUpScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = viewModel(factory = MyViewModelProvider.factory)
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val toastMessage = stringResource(R.string.signed_up_successfully)
    val genders = listOf(Gender.OTHER, Gender.MALE, Gender.FEMALE)

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    navController.navigate(route = GraphRoute.HomeGraph.name) {
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
        HeaderText(textId = R.string.sign_up, textAlign = TextAlign.Center)

        Spacer(Modifier.height(16.dp))

        InputTextField(
            value = state.name,
            onValueChange = {
                viewModel.onEvent(AuthEvent.NameChanged(it))
            },
            labelId = R.string.name,
            isError = state.nameErrorId != null,
            leadingIcon = Icons.Default.Person,
            trailingIcon = Icons.Default.Clear,
            onTrailingIconClick = {
                viewModel.onEvent(AuthEvent.NameChanged(""))
            }
        )

        state.nameErrorId?.let {
            ErrorText(messageId = it)
        }

        Spacer(Modifier.height(16.dp))

        InputTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(AuthEvent.EmailChanged(it))
            },
            labelId = R.string.email,
            isError = state.emailErrorId != null,
            leadingIcon = Icons.Default.Email,
            trailingIcon = Icons.Default.Clear,
            onTrailingIconClick = {
                viewModel.onEvent(AuthEvent.EmailChanged(""))
            },
            keyboardType = KeyboardType.Email
        )

        state.emailErrorId?.let {
            ErrorText(messageId = it)
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputTextField(
                modifier = Modifier.weight(1f),
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(AuthEvent.PasswordChanged(it))
                },
                labelId = R.string.password,
                isError = state.passwordErrorId != null,
                leadingIcon = Icons.Default.Lock,
                trailingIcon = Icons.Default.Clear,
                onTrailingIconClick = {
                    viewModel.onEvent(AuthEvent.PasswordChanged(""))
                },
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )

            Spacer(Modifier.width(8.dp))

            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 4.dp)
                    .clickable {
                        viewModel.onEvent(AuthEvent.PasswordVisibilityChanged(!state.isPasswordVisible))
                    },
                painter = painterResource(if (state.isPasswordVisible) R.drawable.visible_24 else R.drawable.hidden_24),
                contentDescription = stringResource(R.string.visibility),
                tint = White
            )
        }

        state.passwordErrorId?.let {
            ErrorText(messageId = it)
        }

        Spacer(Modifier.height(16.dp))

        RadioGroup(
            selected = state.gender,
            onSelectionChange = {
                viewModel.onEvent(AuthEvent.GenderChanged(it))
            },
            options = genders,
            type = 0,
            modifier = Modifier.padding(end = 16.dp)
        )

        Spacer(Modifier.height(16.dp))

        ActionButton(
            textId = R.string.sign_up,
            onClick = {
                viewModel.onEvent(AuthEvent.SubmitForm(type = 0))
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        FooterText(
            textId = R.string.already_have_an_account,
            hypertextId = R.string.sign_in,
            onClick = {
                navController.navigate(Screen.SignIn.route) {
                    popUpTo(Screen.SignUp.route) {
                        inclusive = true
                    }

                    launchSingleTop = true
                }
            }
        )
    }
}
