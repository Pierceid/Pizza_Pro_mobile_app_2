package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
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


//TODO rearrange it whole (maybe)
@Composable
fun CartPizzaCard(pizza: Pizza, onCountChanged: (Pizza) -> Unit, onClick: () -> Unit) {
    val countState = remember { mutableIntStateOf(pizza.count) }

    Card(
        modifier = Modifier
            .height(84.dp)
            .drawBehind {
                RoundedCornerShape(8.dp)
            },
        border = BorderStroke(1.dp, White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                shape = RectangleShape,
                contentPadding = PaddingValues(0.dp),
                onClick = onClick
            ) {
                Image(
                    modifier = Modifier.aspectRatio(4f / 3f),
                    painter = painterResource(id = pizza.imageSource),
                    contentDescription = stringResource(id = R.string.pizza_image),
                    contentScale = ContentScale.FillBounds
                )
            }

            Column {
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
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedIconButton(
                    modifier = Modifier.size(40.dp),
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ),
                    onClick = {
                        if (countState.intValue < 10) {
                            countState.intValue++
                            onCountChanged(pizza.copy(count = countState.intValue))
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = stringResource(id = R.string.plus)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(BorderStroke(1.dp, MaterialTheme.colorScheme.onTertiaryContainer)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = countState.intValue.toString(),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }

                OutlinedIconButton(
                    modifier = Modifier.size(40.dp),
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    ),
                    onClick = {
                        if (countState.intValue > 0) {
                            countState.intValue--
                            onCountChanged(pizza.copy(count = countState.intValue))
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.minus),
                        contentDescription = stringResource(id = R.string.plus)
                    )
                }
            }
        }
    }
}