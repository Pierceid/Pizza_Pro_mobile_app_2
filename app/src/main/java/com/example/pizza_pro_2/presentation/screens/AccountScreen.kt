package com.example.pizza_pro_2.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.database.MyViewModelProvider
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.domain.ValidationEvent
import com.example.pizza_pro_2.domain.auth.AuthEvent
import com.example.pizza_pro_2.domain.auth.AuthState
import com.example.pizza_pro_2.domain.auth.AuthViewModel
import com.example.pizza_pro_2.domain.shared.SharedEvent
import com.example.pizza_pro_2.domain.shared.SharedState
import com.example.pizza_pro_2.options.Gender
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.ErrorText
import com.example.pizza_pro_2.presentation.components.InfoDialog
import com.example.pizza_pro_2.presentation.components.InputTextField
import com.example.pizza_pro_2.presentation.components.RadioGroup
import com.example.pizza_pro_2.ui.theme.Maroon
import com.example.pizza_pro_2.ui.theme.Teal
import com.example.pizza_pro_2.ui.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@Composable
fun AccountScreen(
    navController: NavController,
    sharedState: SharedState,
    onSharedEvent: (SharedEvent) -> Unit
) {
    val viewModel: AuthViewModel = viewModel(factory = MyViewModelProvider.factory)
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val toastMessage = stringResource(id = R.string.update_account)

    var isDialogVisible by rememberSaveable { mutableStateOf(false) }
    var option by rememberSaveable { mutableIntStateOf(0) }

    val dialogTitle = stringResource(
        id = if (option == 1) R.string.delete_account else R.string.log_out
    )
    val dialogText = stringResource(
        id = if (option == 1) R.string.are_you_certain_you_want_to_proceed_with_deleting_your_account
        else R.string.are_you_certain_you_want_to_log_out_of_your_account
    )
    val color = if (option == 1) Maroon else Teal

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        onSharedEvent(
                            SharedEvent.UpdateAccount(
                                name = state.name,
                                email = state.email,
                                password = state.password,
                                gender = state.gender
                            )
                        )
                    }
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val user = sharedState.currentUser ?: User("", "", "", Gender.OTHER)

    user.let {
        viewModel.state.value = AuthState(
            name = it.name,
            email = it.email,
            password = it.password,
            gender = it.gender
        )
    }

    DefaultColumn {
        if (isDialogVisible) {
            InfoDialog(
                title = dialogTitle,
                text = dialogText,
                onDismiss = { isDialogVisible = it },
                dismissButton = R.string.no,
                onConfirm = {
                    if (option == 1) {
                        CoroutineScope(Dispatchers.IO).launch {
                            onSharedEvent(
                                SharedEvent.DeleteAccount(
                                    name = user.name,
                                    email = user.email
                                )
                            )
                            delay(200)
                            exitProcess(0)
                        }
                    } else if (option == 2) {
                        exitProcess(0)
                    }
                },
                confirmButton = R.string.yes,
                color = color
            )
        }

        Column(
            modifier = Modifier.width(480.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(160.dp)
                    .padding(top = 8.dp)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(80.dp)
                    )
                    .border(
                        border = BorderStroke(width = 1.dp, color = White),
                        shape = RoundedCornerShape(80.dp)
                    ),
                painter = painterResource(id = R.drawable.profile_male),
                contentDescription = stringResource(id = R.string.profile_picture),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            InputTextField(
                value = state.name,
                onValueChange = {
                    viewModel.onEvent(AuthEvent.NameChanged(it))
                },
                label = stringResource(id = R.string.name),
                isError = state.nameError != null,
                leadingIcon = Icons.Default.Person,
                trailingIcon = Icons.Default.Create,
                onTrailingIconClick = {
                    viewModel.onEvent(AuthEvent.NameEdited(!state.isNameEdited))
                },
                imeAction = ImeAction.Done,
                readOnly = !state.isNameEdited
            )

            state.nameError?.let {
                ErrorText(message = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            InputTextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(AuthEvent.EmailChanged(it))
                },
                label = stringResource(id = R.string.email),
                isError = state.emailError != null,
                leadingIcon = Icons.Default.Email,
                trailingIcon = Icons.Default.Create,
                onTrailingIconClick = {
                    viewModel.onEvent(AuthEvent.EmailEdited(!state.isEmailEdited))
                },
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                readOnly = !state.isEmailEdited
            )

            state.emailError?.let {
                ErrorText(message = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            InputTextField(
                value = if (state.isPasswordEdited) state.password else state.password.map { '*' }.joinToString(separator = ""),
                onValueChange = {
                    viewModel.onEvent(AuthEvent.PasswordChanged(it))
                },
                label = stringResource(id = R.string.password),
                isError = state.passwordError != null,
                leadingIcon = Icons.Default.Lock,
                trailingIcon = Icons.Default.Create,
                onTrailingIconClick = {
                    viewModel.onEvent(AuthEvent.PasswordEdited(!state.isPasswordEdited))
                },
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                visualTransformation = if (state.isPasswordEdited) VisualTransformation.None else PasswordVisualTransformation(),
                readOnly = !state.isPasswordEdited
            )

            state.passwordError?.let {
                ErrorText(message = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!state.isGenderEdited) {
                InputTextField(
                    value = state.gender.toString(),
                    onValueChange = { },
                    label = stringResource(id = R.string.gender),
                    leadingIcon = Icons.Default.Face,
                    trailingIcon = Icons.Default.Create,
                    onTrailingIconClick = {
                        viewModel.onEvent(AuthEvent.GenderEdited(true))
                    },
                    readOnly = true
                )
            } else {
                RadioGroup(
                    selected = state.gender,
                    onSelectionChange = {
                        viewModel.onEvent(AuthEvent.GenderChanged(it))
                        viewModel.onEvent(AuthEvent.GenderEdited(false))
                    },
                    options = listOf(Gender.OTHER, Gender.MALE, Gender.FEMALE),
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ActionButton(
            text = stringResource(id = R.string.save),
            onClick = {
                option = 0
                viewModel.onEvent(AuthEvent.Submit(type = 2, currentUser = user))
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.width(480.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionButton(
                text = stringResource(id = R.string.delete),
                onClick = {
                    option = 1
                    isDialogVisible = true
                },
                modifier = Modifier.weight(1f)
            )

            ActionButton(
                text = stringResource(id = R.string.log_out),
                onClick = {
                    option = 2
                    isDialogVisible = true
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
