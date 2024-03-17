package com.example.pizza_pro_2.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizza_pro_2.component.DefaultColumn
import com.example.pizza_pro_2.component.PizzaItem
import com.example.pizza_pro_2.data.DataSource
import com.example.pizza_pro_2.item.Pizza
import com.example.pizza_pro_2.navigation.DETAIL_GRAPH_ROUTE
import com.example.pizza_pro_2.view_model.SharedViewModel

@Composable
fun ShopScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val pizzas: MutableList<Pizza> = remember { DataSource().loadData() }
    val updatedPizzas = remember { mutableStateListOf(*pizzas.toTypedArray()) }

    DefaultColumn {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            columns = GridCells.Adaptive(160.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = updatedPizzas, key = { it.id!! }) { pizza ->
                PizzaItem(
                    pizza = pizza,
                    onCountChanged = { updatedPizza ->
                        val index = pizzas.indexOfFirst { it.id == updatedPizza.id }
                        if (index != -1) {
                            pizzas[index] = updatedPizza
                            updatedPizzas[index] = updatedPizza
                        }
                    },
                    onClick = {
                        sharedViewModel.addPizza(pizza)
                        navController.navigate(DETAIL_GRAPH_ROUTE) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
