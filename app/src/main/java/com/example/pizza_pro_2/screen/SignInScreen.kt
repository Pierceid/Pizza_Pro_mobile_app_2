package com.example.pizza_pro_2.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.component.ActionButton
import com.example.pizza_pro_2.component.AuthTextField
import com.example.pizza_pro_2.component.DefaultColumn
import com.example.pizza_pro_2.component.FooterText
import com.example.pizza_pro_2.component.HeaderText
import com.example.pizza_pro_2.navigation.HOME_GRAPH_ROUTE
import com.example.pizza_pro_2.navigation.Screen
import com.example.pizza_pro_2.ui.theme.Pizza_Pro_2_Theme

@Composable
fun SignInScreen(navController: NavController) {
    val (email, setEmail) = rememberSaveable { mutableStateOf("") }
    val (password, setPassword) = rememberSaveable { mutableStateOf("") }
    val (location, setLocation) = rememberSaveable { mutableStateOf("") }

    DefaultColumn {
        HeaderText(text = stringResource(id = R.string.sign_in))

        Spacer(modifier = Modifier.height(20.dp))

        AuthTextField(
            value = email,
            onValueChange = setEmail,
            label = stringResource(id = R.string.email),
            leadingIcon = Icons.Default.Email
        )

        Spacer(modifier = Modifier.height(8.dp))

        AuthTextField(
            value = password,
            onValueChange = setPassword,
            label = stringResource(id = R.string.password),
            leadingIcon = Icons.Default.Lock,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(8.dp))

        AuthTextField(
            value = location,
            onValueChange = setLocation,
            label = stringResource(id = R.string.location),
            leadingIcon = Icons.Default.LocationOn
        )

        Spacer(modifier = Modifier.height(40.dp))

        ActionButton(
            text = stringResource(id = R.string.sign_in),
            onClick = {
                if (validateInput(email, password, location)) {
                    navController.navigate(HOME_GRAPH_ROUTE) {
                        popUpTo(Screen.SignUp.route) {
                            inclusive = true
                        }
                    }
                }
            },
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

private fun validateInput(
    email: String,
    password: String,
    location: String
): Boolean {
    return /*email.isNotEmpty() && password.isNotEmpty() && location.isNotEmpty()*/ true
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    Pizza_Pro_2_Theme {
        SignInScreen(navController = rememberNavController())
    }
}