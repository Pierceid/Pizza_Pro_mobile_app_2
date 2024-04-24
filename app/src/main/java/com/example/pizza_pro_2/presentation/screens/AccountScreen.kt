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
import androidx.compose.material.icons.filled.Clear
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
import com.example.pizza_pro_2.domain.ValidationEvent
import com.example.pizza_pro_2.domain.auth.AuthEvent
import com.example.pizza_pro_2.domain.auth.AuthViewModel
import com.example.pizza_pro_2.domain.shared.SharedEvent
import com.example.pizza_pro_2.domain.shared.SharedState
import com.example.pizza_pro_2.options.Gender
import com.example.pizza_pro_2.presentation.MyViewModelProvider
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
    var isInitialized by rememberSaveable { mutableStateOf(false) }
    var isDialogVisible by rememberSaveable { mutableStateOf(false) }
    var option by rememberSaveable { mutableIntStateOf(0) }

    val viewModel: AuthViewModel = viewModel(factory = MyViewModelProvider.factory)
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val dialogTitleId = if (option == 1) R.string.delete_account else R.string.log_out
    val dialogTextId =
        if (option == 1) R.string.are_you_certain_you_want_to_proceed_with_deleting_your_account
        else R.string.are_you_certain_you_want_to_log_out_of_your_account
    val toastMessage = stringResource(R.string.account_updated_successfully)
    val color = if (option == 1) Maroon else Teal

    if (!isInitialized) {
        viewModel.refresh(sharedState.currentUser)
        isInitialized = true
    }

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
                        delay(200)
                    }
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    DefaultColumn {
        if (isDialogVisible) {
            InfoDialog(
                titleId = dialogTitleId,
                textId = dialogTextId,
                onDismiss = { isDialogVisible = it },
                dismissButton = R.string.no,
                onConfirm = {
                    if (option == 1) {
                        CoroutineScope(Dispatchers.IO).launch {
                            onSharedEvent(SharedEvent.DeleteAccount(state.name, state.email))
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
                    .background(White, RoundedCornerShape(80.dp))
                    .border(BorderStroke(1.dp, White), RoundedCornerShape(80.dp)),
                painter = painterResource(R.drawable.profile_male),
                contentDescription = stringResource(R.string.profile_picture),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(16.dp))

            InputTextField(
                value = state.name,
                onValueChange = {
                    viewModel.onEvent(AuthEvent.NameChanged(it))
                },
                labelId = R.string.name,
                isError = state.nameErrorId != null,
                leadingIcon = Icons.Default.Person,
                trailingIcon = if (state.isNameEdited) Icons.Default.Clear else Icons.Default.Create,
                onTrailingIconClick = {
                    if (state.isNameEdited) {
                        viewModel.onEvent(AuthEvent.NameChanged(""))
                    } else {
                        viewModel.onEvent(AuthEvent.NameEdited(true))
                        viewModel.onEvent(AuthEvent.EmailEdited(false))
                        viewModel.onEvent(AuthEvent.PasswordEdited(false))
                        viewModel.onEvent(AuthEvent.GenderEdited(false))
                    }
                },
                imeAction = ImeAction.Done,
                readOnly = !state.isNameEdited
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
                trailingIcon = if (state.isEmailEdited) Icons.Default.Clear else Icons.Default.Create,
                onTrailingIconClick = {
                    if (state.isEmailEdited) {
                        viewModel.onEvent(AuthEvent.EmailChanged(""))
                    } else {
                        viewModel.onEvent(AuthEvent.NameEdited(false))
                        viewModel.onEvent(AuthEvent.EmailEdited(true))
                        viewModel.onEvent(AuthEvent.PasswordEdited(false))
                        viewModel.onEvent(AuthEvent.GenderEdited(false))
                    }
                },
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                readOnly = !state.isEmailEdited
            )

            state.emailErrorId?.let {
                ErrorText(messageId = it)
            }

            Spacer(Modifier.height(16.dp))

            InputTextField(
                value = if (state.isPasswordEdited) state.password else state.password.map { '*' }
                    .joinToString(separator = ""),
                onValueChange = {
                    viewModel.onEvent(AuthEvent.PasswordChanged(it))
                },
                labelId = R.string.password,
                isError = state.passwordErrorId != null,
                leadingIcon = Icons.Default.Lock,
                trailingIcon = if (state.isPasswordEdited) Icons.Default.Clear else Icons.Default.Create,
                onTrailingIconClick = {
                    if (state.isPasswordEdited) {
                        viewModel.onEvent(AuthEvent.PasswordChanged(""))
                    } else {
                        viewModel.onEvent(AuthEvent.NameEdited(false))
                        viewModel.onEvent(AuthEvent.EmailEdited(false))
                        viewModel.onEvent(AuthEvent.PasswordEdited(true))
                        viewModel.onEvent(AuthEvent.GenderEdited(false))
                    }
                },
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                visualTransformation = if (state.isPasswordEdited) VisualTransformation.None else PasswordVisualTransformation(),
                readOnly = !state.isPasswordEdited
            )

            state.passwordErrorId?.let {
                ErrorText(messageId = it)
            }

            Spacer(Modifier.height(16.dp))

            if (!state.isGenderEdited) {
                InputTextField(
                    value = state.gender.toString(),
                    onValueChange = { },
                    labelId = R.string.gender,
                    leadingIcon = Icons.Default.Face,
                    trailingIcon = Icons.Default.Create,
                    onTrailingIconClick = {
                        viewModel.onEvent(AuthEvent.NameEdited(false))
                        viewModel.onEvent(AuthEvent.EmailEdited(false))
                        viewModel.onEvent(AuthEvent.PasswordEdited(false))
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

        Spacer(Modifier.height(24.dp))

        ActionButton(
            textId = R.string.save,
            onClick = {
                option = 0
                viewModel.onEvent(AuthEvent.Submit(2, sharedState.currentUser))
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.width(480.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionButton(
                textId = R.string.delete,
                onClick = {
                    option = 1
                    isDialogVisible = true
                },
                modifier = Modifier.weight(1f)
            )

            ActionButton(
                textId = R.string.log_out,
                onClick = {
                    option = 2
                    isDialogVisible = true
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
