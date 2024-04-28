package com.example.pizza_pro_2.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.shared.SharedEvent
import com.example.pizza_pro_2.domain.shared.SharedState
import com.example.pizza_pro_2.domain.shop.ShopEvent
import com.example.pizza_pro_2.domain.shop.ShopViewModel
import com.example.pizza_pro_2.navigation.BottomSheet
import com.example.pizza_pro_2.options.PizzaSortType
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.InputTextField
import com.example.pizza_pro_2.presentation.components.RadioGroup
import com.example.pizza_pro_2.presentation.components.ShopPizzaCard
import com.example.pizza_pro_2.util.Util.Companion.capitalizeText

@Composable
fun ShopScreen(
    navController: NavController,
    sharedState: SharedState,
    onSharedEvent: (SharedEvent) -> Unit
) {
    val viewModel: ShopViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val gridState = rememberLazyGridState()
    val sortTypes = listOf(PizzaSortType.NAME, PizzaSortType.RATING, PizzaSortType.PRICE)

    onSharedEvent(SharedEvent.FilterPizzas(state.sortType, state.searchQuery))

    LaunchedEffect(key1 = state.sortType, key2 = state.searchQuery) {
        gridState.scrollToItem(index = 0)
    }

    DefaultColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        InputTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(ShopEvent.SearchQueryChanged(it))
                onSharedEvent(SharedEvent.FilterPizzas(state.sortType, it))
            },
            labelId = R.string.search,
            leadingIcon = Icons.Default.Search,
            trailingIcon = Icons.Default.Clear,
            onTrailingIconClick = {
                viewModel.onEvent(ShopEvent.SearchQueryChanged(""))
                onSharedEvent(SharedEvent.FilterPizzas(state.sortType, ""))
            },
            imeAction = ImeAction.Done
        )

        RadioGroup(
            selected = state.sortType,
            onSelectionChange = {
                viewModel.onEvent(ShopEvent.SortTypeChanged(it))
                onSharedEvent(SharedEvent.FilterPizzas(it, state.searchQuery))
            },
            options = sortTypes,
            type = 1
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            columns = GridCells.Adaptive(168.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = gridState
        ) {
            items(items = sharedState.filteredPizzas, key = { it.id!! }) { pizza ->
                ShopPizzaCard(
                    pizza = pizza,
                    onCountChanged = {
                        onSharedEvent(SharedEvent.PizzaCountChanged(it))
                        val toastMessage =
                            "${it.name!!.capitalizeText()} Pizza\nwas added to your cart!"
                        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                    },
                    onClick = {
                        onSharedEvent(SharedEvent.PizzaSelectionChanged(pizza))
                    }
                )
            }
        }
    }

    sharedState.selectedPizza?.let {
        BottomSheet(sharedState.selectedPizza) {
            onSharedEvent(SharedEvent.PizzaSelectionChanged(null))
        }
    }
}
