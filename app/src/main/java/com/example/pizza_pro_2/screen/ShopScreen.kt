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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pizza_pro_2.component.DefaultColumn
import com.example.pizza_pro_2.component.PizzaItem
import com.example.pizza_pro_2.data.DataSource
import com.example.pizza_pro_2.item.Pizza
import com.example.pizza_pro_2.navigation.Screen
import com.example.pizza_pro_2.ui.theme.Pizza_Pro_2_Theme

@Composable
fun ShopScreen(navController: NavController) {
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
                    onClick = { navController.navigate(Screen.Detail.route) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopPreview() {
    Pizza_Pro_2_Theme {
        ShopScreen(navController = rememberNavController())
    }
}
