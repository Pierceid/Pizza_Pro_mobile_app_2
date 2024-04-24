package com.example.pizza_pro_2.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.domain.shared.SharedEvent
import com.example.pizza_pro_2.domain.shared.SharedState
import com.example.pizza_pro_2.navigation.BottomSheet
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.CartPizzaCard
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.HeaderText
import com.example.pizza_pro_2.presentation.components.InfoDialog
import com.example.pizza_pro_2.ui.theme.Maroon
import com.example.pizza_pro_2.ui.theme.Silver
import com.example.pizza_pro_2.ui.theme.Teal
import com.example.pizza_pro_2.ui.theme.White
import com.example.pizza_pro_2.util.Util.Companion.formatPrice
import com.example.pizza_pro_2.util.Util.Companion.formatTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    navController: NavController,
    sharedState: SharedState,
    onSharedEvent: (SharedEvent) -> Unit
) {
    var isSheetOpened by rememberSaveable { mutableStateOf(false) }
    var isDialogVisible by rememberSaveable { mutableStateOf(false) }
    var option by rememberSaveable { mutableIntStateOf(0) }

    val context = LocalContext.current
    val items = sharedState.itemsCost
    val delivery = if (items == 0.0) 0.0 else 5.0
    val total = items + delivery
    val dialogTitleId = if (option == 0) R.string.discard_order else R.string.place_order
    val dialogTextId =
        if (option == 0) R.string.are_you_sure_you_want_to_discard_your_order
        else R.string.would_you_like_to_proceed_and_place_your_order
    val toastMessage = stringResource(
        if (option == 0) R.string.order_discarded_successfully
        else R.string.order_placed_successfully
    )
    val color = if (option == 0) Maroon else Teal

    DefaultColumn {
        if (isDialogVisible) {
            InfoDialog(
                titleId = dialogTitleId,
                textId = dialogTextId,
                onDismiss = { isDialogVisible = it },
                dismissButton = R.string.no,
                onConfirm = {
                    if (option == 1) {
                        CoroutineScope(Dispatchers.IO).launch {
                            onSharedEvent(SharedEvent.PlaceOrder(createOrder(sharedState, total)))
                        }
                    }
                    onSharedEvent(SharedEvent.DiscardOrder)
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                },
                confirmButton = R.string.yes,
                color = color
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = sharedState.orderedPizzas, key = { it.id!! }) { pizza ->
                CartPizzaCard(
                    pizza = pizza,
                    onCountChanged = {
                        onSharedEvent(SharedEvent.PizzaCountChanged(it))
                    },
                    onClick = {
                        onSharedEvent(SharedEvent.PizzaSelectionChanged(pizza))
                        isSheetOpened = true
                    }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderText(
                textId = R.string.items,
                textStyle = MaterialTheme.typography.titleMedium,
                color = Silver
            )
            Text(
                text = items.formatPrice(),
                style = MaterialTheme.typography.titleMedium,
                color = Silver
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderText(
                textId = R.string.delivery,
                textStyle = MaterialTheme.typography.titleMedium,
                color = Silver
            )
            Text(
                text = delivery.formatPrice(),
                style = MaterialTheme.typography.titleMedium,
                color = Silver
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .height(2.dp)
                .background(Silver)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderText(
                textId = R.string.total,
                color = White
            )
            Text(
                text = total.formatPrice(),
                style = MaterialTheme.typography.headlineLarge,
                color = White
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ActionButton(
                textId = R.string.discard,
                onClick = {
                    option = 0
                    isDialogVisible = true
                },
                modifier = Modifier.weight(1f)
            )

            ActionButton(
                textId = R.string.order,
                onClick = {
                    option = 1
                    isDialogVisible = true
                },
                modifier = Modifier.weight(1f)
            )
        }
    }

    if (isSheetOpened) {
        BottomSheet(sharedState) { isSheetOpened = it }
    }
}

private fun createOrder(sharedState: SharedState, total: Double): Order {
    return Order(
        name = sharedState.currentUser!!.name,
        time = System.currentTimeMillis().formatTime(),
        place = "none",
        items = sharedState.orderedPizzas.sumOf { it.count },
        cost = total.formatPrice()
    )
}
