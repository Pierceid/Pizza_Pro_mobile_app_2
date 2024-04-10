package com.example.pizza_pro_2.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.domain.shared.SharedFormEvent
import com.example.pizza_pro_2.domain.shared.SharedFormState
import com.example.pizza_pro_2.options.Gender
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.InputTextField
import com.example.pizza_pro_2.ui.theme.White
import kotlin.system.exitProcess

@Composable
fun AccountScreen(
    navController: NavController,
    sharedState: SharedFormState,
    onSharedEvent: (SharedFormEvent) -> Unit
) {
    val user = sharedState.currentUser ?: User(
        name = "Jozef",
        email = "j@j.j",
        password = "jjjj8888",
        gender = Gender.MALE
    )

    DefaultColumn {
        Column(
            modifier = Modifier.width(480.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(160.dp)
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

            InputTextField(
                value = user.name,
                onValueChange = { },
                label = stringResource(id = R.string.name),
                leadingIcon = Icons.Default.Person,
                trailingIcon = Icons.Default.Create,
                onTrailingIconClick = { },
                readOnly = true
            )

            InputTextField(
                value = user.email,
                onValueChange = { },
                label = stringResource(id = R.string.email),
                leadingIcon = Icons.Default.Email,
                trailingIcon = Icons.Default.Create,
                onTrailingIconClick = { },
                readOnly = true
            )

            InputTextField(
                value = user.password.map { '*' }.joinToString(separator = ""),
                onValueChange = { },
                label = stringResource(id = R.string.password),
                leadingIcon = Icons.Default.Lock,
                trailingIcon = Icons.Default.Create,
                onTrailingIconClick = { },
                readOnly = true
            )

            InputTextField(
                value = user.gender.toString(),
                onValueChange = { },
                label = stringResource(id = R.string.gender),
                leadingIcon = Icons.Default.Face,
                trailingIcon = Icons.Default.Create,
                onTrailingIconClick = { },
                readOnly = true
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.width(480.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionButton(
                text = stringResource(id = R.string.delete),
                onClick = {

                },
                modifier = Modifier.weight(1f)
            )

            ActionButton(
                text = stringResource(id = R.string.log_out),
                onClick = {
                    exitProcess(0)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
