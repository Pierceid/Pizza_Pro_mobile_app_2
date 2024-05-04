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
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.MyViewModelProvider
import com.example.pizza_pro_2.domain.ValidationEvent
import com.example.pizza_pro_2.domain.account.AccountEvent
import com.example.pizza_pro_2.domain.account.AccountViewModel
import com.example.pizza_pro_2.options.Gender
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.ErrorText
import com.example.pizza_pro_2.presentation.components.InfoDialog
import com.example.pizza_pro_2.presentation.components.InputTextField
import com.example.pizza_pro_2.presentation.components.RadioGroup
import com.example.pizza_pro_2.ui.theme.White

@Composable
fun AccountScreen() {
    val viewModel: AccountViewModel = viewModel(factory = MyViewModelProvider.factory)
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val genders = listOf(Gender.OTHER, Gender.MALE, Gender.FEMALE)

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {
                    viewModel.onEvent(AccountEvent.DialogVisibilityChanged(true))
                }
            }
        }
    }

    DefaultColumn {
        if (state.isDialogVisible) {
            val toastMessage = stringResource(state.toastMessageId)

            InfoDialog(
                titleId = state.dialogTitleId,
                textId = state.dialogTextId,
                onDismiss = {
                    viewModel.onEvent(AccountEvent.DialogVisibilityChanged(false))
                },
                dismissButton = R.string.no,
                onConfirm = {
                    state.dialogEvent?.let {
                        viewModel.onEvent(it)
                    }
                    viewModel.onEvent(AccountEvent.DialogVisibilityChanged(false))
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                },
                confirmButton = R.string.yes,
                color = state.dialogColor
            )
        }

        Column(
            modifier = Modifier.width(480.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(160.dp)
                    .background(White, RoundedCornerShape(80.dp))
                    .border(BorderStroke(2.dp, White), RoundedCornerShape(80.dp)),
                painter = painterResource(state.imageId),
                contentDescription = stringResource(R.string.profile_picture),
                contentScale = ContentScale.FillBounds
            )

            Spacer(Modifier.height(16.dp))

            InputTextField(
                value = state.name,
                onValueChange = {
                    viewModel.onEvent(AccountEvent.NameChanged(it))
                },
                labelId = R.string.name,
                isError = state.nameErrorId != null,
                leadingIcon = Icons.Default.Person,
                trailingIcon = if (state.isNameEdited) Icons.Default.Clear else Icons.Default.Create,
                onTrailingIconClick = {
                    if (state.isNameEdited) {
                        viewModel.onEvent(AccountEvent.NameChanged(""))
                    } else {
                        viewModel.onEvent(AccountEvent.NameEdited(true))
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
                    viewModel.onEvent(AccountEvent.EmailChanged(it))
                },
                labelId = R.string.email,
                isError = state.emailErrorId != null,
                leadingIcon = Icons.Default.Email,
                trailingIcon = if (state.isEmailEdited) Icons.Default.Clear else Icons.Default.Create,
                onTrailingIconClick = {
                    if (state.isEmailEdited) {
                        viewModel.onEvent(AccountEvent.EmailChanged(""))
                    } else {
                        viewModel.onEvent(AccountEvent.EmailEdited(true))
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
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(AccountEvent.PasswordChanged(it))
                },
                labelId = R.string.password,
                isError = state.passwordErrorId != null,
                leadingIcon = Icons.Default.Lock,
                trailingIcon = if (state.isPasswordEdited) Icons.Default.Clear else Icons.Default.Create,
                onTrailingIconClick = {
                    if (state.isPasswordEdited) {
                        viewModel.onEvent(AccountEvent.PasswordChanged(""))
                    } else {
                        viewModel.onEvent(AccountEvent.PasswordEdited(true))
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
                        viewModel.onEvent(AccountEvent.GenderEdited(true))
                    },
                    readOnly = true
                )
            } else {
                RadioGroup(
                    selected = state.gender,
                    onSelectionChange = {
                        viewModel.onEvent(AccountEvent.GenderChanged(it))
                        viewModel.onEvent(AccountEvent.GenderEdited(false))
                    },
                    options = genders,
                    type = 0,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        ActionButton(
            textId = R.string.save,
            onClick = {
                viewModel.onEvent(AccountEvent.OptionChanged(0))
                viewModel.onEvent(AccountEvent.SubmitForm(2))
            },
            modifier = Modifier.width(480.dp)
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.width(480.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionButton(
                textId = R.string.delete,
                onClick = {
                    viewModel.onEvent(AccountEvent.OptionChanged(1))
                },
                modifier = Modifier.weight(1f)
            )

            ActionButton(
                textId = R.string.sign_out,
                onClick = {
                    viewModel.onEvent(AccountEvent.OptionChanged(2))
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
