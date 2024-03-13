package com.example.pizza_pro_2.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.navigation.HOME_GRAPH_ROUTE
import com.example.pizza_pro_2.navigation.Screen
import com.example.pizza_pro_2.ui.theme.PizzaProBackground
import com.example.pizza_pro_2.ui.theme.PizzaProButton
import com.example.pizza_pro_2.ui.theme.PizzaProCard
import com.example.pizza_pro_2.ui.theme.Pizza_Pro_2_Theme
import com.example.pizza_pro_2.ui.theme.Purple
import com.example.pizza_pro_2.ui.theme.Slate

@Composable
fun LoginScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }

    PizzaProBackground {
        PizzaProCard {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 12.dp, 12.dp, 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.log_in),
                    style = MaterialTheme.typography.titleLarge,
                    color = Purple
                )
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .clickable(
                            onClick = {
                                navController.navigate(Screen.Register.route) {
                                    popUpTo(Screen.Login.route) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        ),
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = stringResource(id = R.string.swap_icon)
                )
                Text(
                    text = stringResource(id = R.string.register),
                    style = MaterialTheme.typography.titleLarge,
                    color = Slate
                )
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text(stringResource(id = R.string.email)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = stringResource(id = R.string.email)
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text(stringResource(id = R.string.password)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = stringResource(id = R.string.password)
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = stringResource(id = R.string.password)
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                visualTransformation = PasswordVisualTransformation()
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                value = location.value,
                onValueChange = { location.value = it },
                label = { Text(stringResource(id = R.string.location)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = stringResource(id = R.string.location)
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium
            )
            PizzaProButton(
                modifier = Modifier
                    .padding(24.dp)
                    .height(60.dp),
                text = stringResource(id = R.string.log_in),
                onClick = {
                    navController.navigate(HOME_GRAPH_ROUTE) {
                        popUpTo(Screen.Intro.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    Pizza_Pro_2_Theme {
        LoginScreen(navController = rememberNavController())
    }
}