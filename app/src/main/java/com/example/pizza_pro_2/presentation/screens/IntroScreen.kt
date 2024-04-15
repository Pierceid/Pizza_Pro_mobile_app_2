package com.example.pizza_pro_2.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.ui.theme.Pizza_Pro_2_Theme

@Composable
fun IntroScreen(navController: NavController) {
    DefaultColumn(verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)) {
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
            text = stringResource(id = R.string.let_s_get_started),
            onClick = {
                /*navController.navigate(GraphRoute.HomeGraph.name) {
                    popUpTo(Screen.Intro.route) {
                        inclusive = true
                    }
                }*/
                navController.navigate(Screen.SignUp.route)
            },
            modifier = Modifier
                .height(120.dp)
                .padding(vertical = 24.dp)
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
