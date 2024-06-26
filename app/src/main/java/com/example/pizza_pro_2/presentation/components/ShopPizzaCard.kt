package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.pizza_pro_2.util.Util.Companion.formatPrice
import kotlin.math.roundToInt

@Composable
fun ShopPizzaCard(pizza: Pizza, onCountChanged: (Pizza) -> Unit, onClick: () -> Unit) {
    val countState = remember { mutableIntStateOf(pizza.count) }

    Card(
        modifier = Modifier.drawBehind { RoundedCornerShape(8.dp) },
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
                    modifier = Modifier.aspectRatio(3f / 2f),
                    painter = painterResource(pizza.imageSource),
                    contentDescription = stringResource(R.string.pizza_image),
                    contentScale = ContentScale.FillBounds
                )
            }

            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                text = pizza.name!!.capitalizeText(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textDecoration = TextDecoration.Underline
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Row {
                        for (i in 1..pizza.rating.roundToInt()) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Default.Star,
                                contentDescription = stringResource(R.string.star),
                                tint = Yellow
                            )
                        }
                    }

                    Spacer(Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(R.drawable.price_24),
                            contentDescription = stringResource(R.string.add_item),
                            tint = Red
                        )

                        Spacer(Modifier.width(4.dp))

                        Text(
                            text = pizza.cost.formatPrice(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Red
                        )
                    }
                }

                Box(modifier = Modifier.padding(bottom = 2.dp)) {
                    IconButton(
                        modifier = Modifier.size(42.dp),
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
                            contentDescription = stringResource(R.string.add_item)
                        )
                    }
                }
            }
        }
    }
}
