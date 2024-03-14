package com.example.pizza_pro_2.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizza_pro_2.data.DataSource
import com.example.pizza_pro_2.item.Pizza
import com.example.pizza_pro_2.navigation.Screen
import com.example.pizza_pro_2.ui.theme.PizzaItem
import com.example.pizza_pro_2.ui.theme.PizzaProBackground

@Composable
fun ShopScreen(navController: NavController) {
    val pizzas: MutableList<Pizza> = remember { DataSource().loadData() }
    val updatedPizzas = remember { mutableStateListOf(*pizzas.toTypedArray()) }

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
                items = updatedPizzas,
                key = { it.id!! },
                itemContent = { pizza ->
                    PizzaItem(
                        pizza = pizza,
                        onCountChanged = { updatedPizza ->
                            val index = pizzas.indexOfFirst { it.id == updatedPizza.id }
                            if (index != -1) {
                                pizzas[index] = updatedPizza
                                updatedPizzas[index] = updatedPizza
                            }
                        },
                        // TODO add pizza as argument
                        onClick = { navController.navigate(Screen.Detail.route) }
                    )
                }
            )
        }
    }
}



