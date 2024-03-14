package com.example.pizza_pro_2.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizza_pro_2.data.DataSource
import com.example.pizza_pro_2.item.Pizza
import com.example.pizza_pro_2.ui.theme.PizzaCard
import com.example.pizza_pro_2.ui.theme.PizzaProBackground

@Composable
fun ShopScreen(navController: NavController) {
    val pizzas: List<Pizza> = DataSource().loadData()

    PizzaProBackground {
        Text(
            text = "shop",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(5.dp)
        )
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(
                items = pizzas,
                itemContent = { pizza ->
                    PizzaCard(pizza = pizza)
                }
            )
        }
    }
}
