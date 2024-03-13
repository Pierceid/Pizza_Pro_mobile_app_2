package com.example.pizza_pro_2.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.navigation.Screen
import com.example.pizza_pro_2.ui.theme.PizzaProButton
import com.example.pizza_pro_2.ui.theme.PizzaProBackground

@Composable
fun IntroScreen(navController: NavController) {
    PizzaProBackground {
        Image(
            modifier = Modifier
                .size(360.dp, 300.dp)
                .padding(20.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.logo),
            contentScale = ContentScale.Fit
        )
        Image(
            modifier = Modifier
                .size(360.dp, 150.dp)
                .padding(20.dp),
            painter = painterResource(id = R.drawable.motto),
            contentDescription = stringResource(id = R.string.motto),
            contentScale = ContentScale.Fit
        )
        PizzaProButton(
            modifier = Modifier.padding(12.dp).height(60.dp),
            text = stringResource(id = R.string.lets_get_started),
            onClick = { navController.navigate(Screen.Register.route) }
        )
    }
}
