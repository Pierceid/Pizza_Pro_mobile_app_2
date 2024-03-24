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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.models.Pizza
import com.example.pizza_pro_2.ui.theme.Red
import com.example.pizza_pro_2.ui.theme.White
import com.example.pizza_pro_2.ui.theme.Yellow
import com.example.pizza_pro_2.util.Util.Companion.capitalizeText
import com.example.pizza_pro_2.util.Util.Companion.formatDouble
import kotlin.math.roundToInt

@Composable
fun CartPizzaCard(pizza: Pizza, onCountChanged: (Pizza) -> Unit, onClick: () -> Unit) {
    val countState = remember { mutableIntStateOf(pizza.count) }

    Card(
        modifier = Modifier.drawBehind {
            RoundedCornerShape(8.dp)
        },
        border = BorderStroke(1.dp, White)
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                shape = RectangleShape,
                contentPadding = PaddingValues(0.dp),
                onClick = onClick
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(3f / 2f),
                    painter = painterResource(id = pizza.imageSource),
                    contentDescription = stringResource(id = R.string.pizza_image),
                    contentScale = ContentScale.FillBounds
                )
            }

            Text(
                text = pizza.name!!.capitalizeText(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textDecoration = TextDecoration.Underline
            )

            Row {
                for (i in 1..pizza.rating.roundToInt()) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.Star,
                        contentDescription = stringResource(id = R.string.star),
                        tint = Yellow
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.price_20),
                    contentDescription = stringResource(id = R.string.add_item),
                    tint = Red
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "${pizza.cost.toString().formatDouble("%.2f")} €",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Red
                )
            }

            Spacer(modifier = Modifier)
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiaryContainer),
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
                    style = MaterialTheme.typography.bodyLarge,
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