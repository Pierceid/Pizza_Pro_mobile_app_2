package com.example.pizza_pro_2.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.ui.theme.Red
import com.example.pizza_pro_2.util.Util.Companion.capitalizeText
import com.example.pizza_pro_2.util.Util.Companion.formatDouble
import com.example.pizza_pro_2.view_models.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val pizza = sharedViewModel.state.selectedPizza!!
    var poppingBackStack by remember { mutableStateOf(false) }

    DefaultColumn(verticalArrangement = Arrangement.Center) {
        Column(
            modifier = Modifier
                .width(480.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(16.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(20.dp),
                painter = painterResource(id = pizza.imageSource),
                contentDescription = stringResource(id = R.string.pizza_image),
                contentScale = ContentScale.FillWidth
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp, 20.dp, 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = stringResource(id = R.string.star)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Rating: ${pizza.rating.toString().formatDouble()}")
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(id = R.string.time)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Time: ${pizza.time} min")
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(id = R.string.flame)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Calories: ${pizza.calories} kcal")
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = pizza.name!!.capitalizeText(),
                style = MaterialTheme.typography.titleMedium,
                color = Red
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp, 20.dp, 20.dp),
                text = pizza.description!!,
                style = MaterialTheme.typography.titleSmall
            )

            ActionButton(
                text = stringResource(id = R.string.go_back),
                onClick = {
                    if (!poppingBackStack) {
                        poppingBackStack = true
                        navController.popBackStack()

                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000)
                            poppingBackStack = false
                        }
                    }
                },
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}
