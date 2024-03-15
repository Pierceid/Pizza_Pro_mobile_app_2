package com.example.pizza_pro_2.screen

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.pizza_pro_2.component.DefaultColumn
import com.example.pizza_pro_2.navigation.Screen

@Composable
fun ProfileScreen(navController: NavController) {
    DefaultColumn {
        Text(
            text = "profile",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.clickable(
                onClick = { navController.navigate(Screen.Detail.route) }
            )
        )
    }
}