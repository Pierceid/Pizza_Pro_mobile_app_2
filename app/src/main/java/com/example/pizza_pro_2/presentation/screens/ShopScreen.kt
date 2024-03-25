package com.example.pizza_pro_2.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.SharedFormEvent
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.InputTextField
import com.example.pizza_pro_2.presentation.components.ShopPizzaCard
import com.example.pizza_pro_2.util.Util.Companion.capitalizeText
import com.example.pizza_pro_2.view_models.SharedViewModel

@Composable
fun ShopScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val state = sharedViewModel.state
    val context = LocalContext.current

    DefaultColumn {
        InputTextField(
            value = state.searchQuery,
            onValueChange = {
                sharedViewModel.onEvent(SharedFormEvent.OnSearchQueryChange(it))
            },
            label = stringResource(id = R.string.search),
            leadingIcon = Icons.Default.Search,
            trailingIcon = Icons.Default.Clear,
            onTrailingIconClick = {
                sharedViewModel.onEvent(SharedFormEvent.OnSearchQueryChange(""))
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            columns = GridCells.Adaptive(168.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = state.filteredPizzas, key = { it.id!! }) { pizza ->
                ShopPizzaCard(
                    pizza = pizza,
                    onCountChanged = {
                        sharedViewModel.onEvent(SharedFormEvent.OnPizzaCountChange(it))
                        val toastMessage = "( ${pizza.name!!.capitalizeText()} Pizza )\nwas added to your cart !"
                        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
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
    }
}
