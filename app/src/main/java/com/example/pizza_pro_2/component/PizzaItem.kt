package com.example.pizza_pro_2.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.item.Pizza
import com.example.pizza_pro_2.ui.theme.Ashen
import com.example.pizza_pro_2.ui.theme.Black
import com.example.pizza_pro_2.ui.theme.Grey
import com.example.pizza_pro_2.ui.theme.Orange
import com.example.pizza_pro_2.ui.theme.Red
import com.example.pizza_pro_2.ui.theme.Sun
import com.example.pizza_pro_2.util.Util.Companion.capitalizeText

@Composable
fun PizzaItem(pizza: Pizza, onCountChanged: (Pizza) -> Unit, onClick: () -> Unit) {
    val countState = remember { mutableIntStateOf(pizza.count) }

    Card(
        modifier = Modifier
            .height(130.dp)
            .padding(top = 8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.horizontalGradient(colors = listOf(Grey, Ashen)))
        ) {
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .border(border = BorderStroke(1.dp, Black))
                    .clickable(onClick = onClick),
                painter = painterResource(id = pizza.imageSource),
                contentDescription = pizza.name,
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = pizza.name!!.capitalizeText(),
                    style = MaterialTheme.typography.titleSmall,
                    color = Red,
                    modifier = Modifier.clickable(onClick = {})
                )
                Text(
                    text = String.format("%.2f €", pizza.cost),
                    style = MaterialTheme.typography.titleSmall,
                    color = Black
                )
                Box(
                    modifier = Modifier
                        .size(120.dp, 40.dp)
                        .background(
                            color = Orange,
                            shape = RoundedCornerShape(40.dp)
                        )
                        .border(
                            border = BorderStroke(1.dp, Black),
                            shape = RoundedCornerShape(40.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = {
                                if (countState.intValue < 10) {
                                    countState.intValue++
                                    onCountChanged(pizza.copy(count = countState.intValue))
                                }
                            },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.plus),
                                contentDescription = stringResource(id = R.string.plus)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = Sun,
                                    shape = RoundedCornerShape(40.dp)
                                )
                                .border(
                                    border = BorderStroke(1.dp, Black),
                                    shape = RoundedCornerShape(40.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = countState.intValue.toString(),
                                style = MaterialTheme.typography.titleSmall,
                                color = Black
                            )
                        }
                        TextButton(
                            onClick = {
                                if (countState.intValue > 0) {
                                    countState.intValue--
                                    onCountChanged(pizza.copy(count = countState.intValue))
                                }
                            },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.minus),
                                contentDescription = stringResource(id = R.string.minus)
                            )
                        }
                    }
                }
            }
        }
    }
}