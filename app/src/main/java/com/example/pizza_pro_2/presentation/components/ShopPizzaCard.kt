package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import com.example.pizza_pro_2.ui.theme.Green
import com.example.pizza_pro_2.ui.theme.Red
import com.example.pizza_pro_2.ui.theme.White
import com.example.pizza_pro_2.ui.theme.Yellow
import com.example.pizza_pro_2.util.Util.Companion.capitalizeText
import com.example.pizza_pro_2.util.Util.Companion.formatDouble
import java.text.NumberFormat
import kotlin.math.roundToInt

@Composable
fun ShopPizzaCard(pizza: Pizza, onCountChanged: (Pizza) -> Unit, onClick: () -> Unit) {
    val countState = remember { mutableIntStateOf(pizza.count) }

    Card(
        modifier = Modifier.drawBehind {
            RoundedCornerShape(8.dp)
        },
        border = BorderStroke(1.dp, White),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column {
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
                modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp),
                text = pizza.name!!.capitalizeText(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textDecoration = TextDecoration.Underline
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
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

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.price_20),
                            contentDescription = stringResource(id = R.string.add_item),
                            tint = Red
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = NumberFormat.getCurrencyInstance().format(pizza.cost).toString().formatDouble(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Red
                        )
                    }
                }

                IconButton(
                    modifier = Modifier.size(48.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Green,
                        contentColor = White
                    ),
                    onClick = {
                        countState.intValue = 1
                        onCountChanged(pizza.copy(count = countState.intValue))
                    }
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(0.8f),
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_item)
                    )
                }
            }
        }
    }
}
