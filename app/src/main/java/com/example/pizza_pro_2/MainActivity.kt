package com.example.pizza_pro_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.pizza_pro_2.navigation.NavGraph
import com.example.pizza_pro_2.ui.theme.Ashen
import com.example.pizza_pro_2.ui.theme.Black
import com.example.pizza_pro_2.ui.theme.Grey
import com.example.pizza_pro_2.ui.theme.Orange
import com.example.pizza_pro_2.ui.theme.Pizza_Pro_2_Theme
import com.example.pizza_pro_2.ui.theme.Red
import com.example.pizza_pro_2.ui.theme.Sun

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pizza_Pro_2_Theme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}

@Composable
fun PizzaCard(
    painter: Painter,
    title: String,
    cost: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.horizontalGradient(colors = listOf(Grey, Ashen)))
        ) {
            Image(
                modifier = Modifier
                    .padding(10.dp)
                    .border(border = BorderStroke(1.dp, Black))
                    .clickable(onClick = {}),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 5.dp, 10.dp, 10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Red,
                    modifier = Modifier.clickable(onClick = {})
                )
                Text(
                    text = cost,
                    style = MaterialTheme.typography.titleLarge,
                    color = Black
                )
                Box(
                    modifier = Modifier
                        .size(126.dp, 42.dp)
                        .background(
                            color = Orange,
                            shape = RoundedCornerShape(42.dp)
                        )
                        .border(
                            border = BorderStroke(1.dp, Black),
                            shape = RoundedCornerShape(42.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.plus),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.clickable(onClick = {})
                        )
                        Box(
                            modifier = Modifier
                                .size(42.dp, 42.dp)
                                .background(
                                    color = Sun,
                                    shape = RoundedCornerShape(42.dp)
                                )
                                .border(
                                    border = BorderStroke(1.dp, Black),
                                    shape = RoundedCornerShape(42.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "0",
                                style = MaterialTheme.typography.titleLarge,
                                color = Black
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.clickable(onClick = {})
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Pizza_Pro_2_Theme {

    }
}