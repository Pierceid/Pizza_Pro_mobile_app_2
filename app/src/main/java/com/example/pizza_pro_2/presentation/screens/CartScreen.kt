package com.example.pizza_pro_2.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.SharedFormEvent
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.HeaderText
import com.example.pizza_pro_2.presentation.components.PizzaItem
import com.example.pizza_pro_2.ui.theme.Silver
import com.example.pizza_pro_2.ui.theme.White
import com.example.pizza_pro_2.util.Util.Companion.formatDouble
import com.example.pizza_pro_2.view_models.SharedViewModel

@Composable
fun CartScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val state = sharedViewModel.state

    val itemsCost = state.itemsCost
    val deliveryServices = state.itemsCost / 10
    val total = itemsCost + deliveryServices

    DefaultColumn {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            columns = GridCells.Adaptive(160.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = state.orderedPizzas, key = { it.id!! }) { pizza ->
                PizzaItem(
                    pizza = pizza,
                    onCountChanged = {
                        sharedViewModel.onEvent(SharedFormEvent.OnPizzaCountChange(it))
                    },
                    onClick = {
                        sharedViewModel.onEvent(SharedFormEvent.OnPizzaSelectionChange(pizza))
                        navController.navigate(DETAIL_GRAPH_ROUTE) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderText(
                text = stringResource(id = R.string.items_cost),
                textStyle = MaterialTheme.typography.titleMedium,
                color = Silver
            )
            HeaderText(
                text = "${itemsCost.toString().formatDouble("%.2f")} €",
                textStyle = MaterialTheme.typography.titleMedium,
                color = Silver
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderText(
                text = stringResource(id = R.string.delivery_services),
                textStyle = MaterialTheme.typography.titleMedium,
                color = Silver
            )
            HeaderText(
                text = "${deliveryServices.toString().formatDouble("%.2f")} €",
                textStyle = MaterialTheme.typography.titleMedium,
                color = Silver
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderText(
                text = stringResource(id = R.string.total),
                color = White
            )
            HeaderText(
                text = "${total.toString().formatDouble("%.2f")} €",
                color = White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        ActionButton(
            text = stringResource(id = R.string.order),
            onClick = { sharedViewModel.onEvent(SharedFormEvent.Refresh) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}