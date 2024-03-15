package com.example.pizza_pro_2.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.component.ActionButton
import com.example.pizza_pro_2.component.DefaultColumn
import com.example.pizza_pro_2.navigation.Screen
import com.example.pizza_pro_2.ui.theme.Pizza_Pro_2_Theme

@Composable
fun IntroScreen(navController: NavController) {
    DefaultColumn (verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically)) {
        Image(
            modifier = Modifier.size(400.dp, 320.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.logo),
            contentScale = ContentScale.Fit
        )
        Image(
            modifier = Modifier.size(400.dp, 160.dp),
            painter = painterResource(id = R.drawable.motto),
            contentDescription = stringResource(id = R.string.motto),
            contentScale = ContentScale.Fit
        )
        ActionButton(
            text = stringResource(id = R.string.lets_get_started),
            onClick = { navController.navigate(Screen.Register.route) },
            modifier = Modifier.height(80.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IntroPreview() {
    Pizza_Pro_2_Theme {
        IntroScreen(navController = rememberNavController())
    }
}

