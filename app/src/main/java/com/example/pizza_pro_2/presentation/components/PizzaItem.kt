package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.models.Pizza
import com.example.pizza_pro_2.ui.theme.Red
import com.example.pizza_pro_2.ui.theme.White
import com.example.pizza_pro_2.util.Util.Companion.capitalizeText
import com.example.pizza_pro_2.util.Util.Companion.formatDouble

@Composable
fun PizzaItem(pizza: Pizza, onCountChanged: (Pizza) -> Unit, onClick: () -> Unit) {
    val countState = remember { mutableIntStateOf(pizza.count) }

    Card(shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, White)) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                shape = RectangleShape,
                onClick = onClick,
                contentPadding = PaddingValues(0.dp)
            ) {
                Image(
                    painter = painterResource(id = pizza.imageSource),
                    contentDescription = stringResource(id = R.string.pizza_picture),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = pizza.name!!.capitalizeText(),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Price: ${pizza.cost.toString().formatDouble("%.2f")} €",
                style = MaterialTheme.typography.titleSmall,
                color = Red
            )

            Spacer(modifier = Modifier.height(4.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    modifier = Modifier.size(40.dp),
                    onClick = {
                        if (countState.intValue < 10) {
                            countState.intValue++
                            onCountChanged(pizza.copy(count = countState.intValue))
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = stringResource(id = R.string.plus),
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }

                Box(
                    modifier = Modifier
                        .size(60.dp, 40.dp)
                        .background(MaterialTheme.colorScheme.tertiary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = countState.intValue.toString(),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }

                TextButton(
                    modifier = Modifier.size(40.dp),
                    onClick = {
                        if (countState.intValue > 0) {
                            countState.intValue--
                            onCountChanged(pizza.copy(count = countState.intValue))
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.minus),
                        contentDescription = stringResource(id = R.string.minus),
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        }
    }
}
