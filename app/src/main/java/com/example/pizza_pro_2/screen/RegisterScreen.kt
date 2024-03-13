package com.example.pizza_pro_2.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.navigation.HOME_GRAPH_ROUTE
import com.example.pizza_pro_2.navigation.Screen
import com.example.pizza_pro_2.ui.theme.Pizza_Pro_2_Theme
import com.example.pizza_pro_2.ui.theme.Purple
import com.example.pizza_pro_2.ui.theme.Slate
import com.example.pizza_pro_2.ui.theme.White

@Composable
fun RegisterScreen(navController: NavController) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.titleLarge,
                color = Purple
            )
            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .clickable(
                        onClick = { navController.navigate(Screen.Login.route) }
                    ),
                imageVector = Icons.Default.ArrowForward,
                contentDescription = stringResource(id = R.string.swap_icon),
            )
            Text(
                text = stringResource(id = R.string.log_in),
                style = MaterialTheme.typography.titleLarge,
                color = Slate
            )
        }
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text(stringResource(id = R.string.name)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(id = R.string.name)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text(stringResource(id = R.string.email)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = stringResource(id = R.string.email)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(stringResource(id = R.string.password)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(id = R.string.password)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        TextField(
            value = location.value,
            onValueChange = { location.value = it },
            label = { Text(stringResource(id = R.string.location)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(id = R.string.location)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Button(
            modifier = Modifier.padding(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple,
                contentColor = White
            ),
            onClick = {
                navController.navigate(HOME_GRAPH_ROUTE) {
                    popUpTo(Screen.Intro.route) {
                        inclusive = true
                    }
                }
            }
        ) {
            Text(text = "Register", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    Pizza_Pro_2_Theme {
        RegisterScreen(navController = rememberNavController())
    }
}