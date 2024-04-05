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
import com.example.pizza_pro_2.domain.shared.SharedFormEvent
import com.example.pizza_pro_2.domain.shared.SharedViewModel
import com.example.pizza_pro_2.navigation.BottomSheet
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.CartPizzaCard
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.HeaderText
import com.example.pizza_pro_2.presentation.components.InfoDialog
import com.example.pizza_pro_2.ui.theme.Silver
import com.example.pizza_pro_2.ui.theme.White
import com.example.pizza_pro_2.util.Util.Companion.formatDouble
import java.text.NumberFormat

@Composable
fun CartScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    var isSheetOpened by rememberSaveable { mutableStateOf(false) }
    var isDialogVisible by rememberSaveable { mutableStateOf(false) }
    var option by rememberSaveable { mutableIntStateOf(0) }

    val state = sharedViewModel.state
    val context = LocalContext.current

    val dialogTitle =
        stringResource(id = if (option == 0) R.string.discard_order else R.string.place_order)
    val dialogText =
        stringResource(id = if (option == 0) R.string.are_you_sure_you_want_to_discard_this_order else R.string.are_you_ready_to_proceed_and_place_your_order)
    val toastMessage =
        stringResource(id = if (option == 0) R.string.discarded_successfully else R.string.ordered_successfully)
    val event = if (option == 0) SharedFormEvent.Discard else SharedFormEvent.Order

    val items = state.itemsCost
    val delivery = if (items == 0.0) 0 else 5
    val total = items + delivery

    DefaultColumn {
        if (isDialogVisible) {
            InfoDialog(
                title = dialogTitle,
                text = dialogText,
                onDismiss = { isDialogVisible = it },
                dismissButton = R.string.no,
                onConfirm = {
                    sharedViewModel.onEvent(event)
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                },
                confirmButton = R.string.yes
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = state.orderedPizzas, key = { it.id!! }) { pizza ->
                CartPizzaCard(
                    pizza = pizza,
                    onCountChanged = {
                        sharedViewModel.onEvent(SharedFormEvent.PizzaCountChanged(it))
                    },
                    onClick = {
                        sharedViewModel.onEvent(SharedFormEvent.PizzaSelectionChanged(pizza))
                        isSheetOpened = true
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderText(
                text = stringResource(id = R.string.items),
                textStyle = MaterialTheme.typography.titleMedium,
                color = Silver
            )
            HeaderText(
                text = NumberFormat.getCurrencyInstance().format(items).toString().formatDouble(),
                textStyle = MaterialTheme.typography.titleMedium,
                color = Silver
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderText(
                text = stringResource(id = R.string.delivery),
                textStyle = MaterialTheme.typography.titleMedium,
                color = Silver
            )
            HeaderText(
                text = NumberFormat.getCurrencyInstance().format(delivery).toString()
                    .formatDouble(),
                textStyle = MaterialTheme.typography.titleMedium,
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
                text = stringResource(id = R.string.total),
                color = White
            )
            HeaderText(
                text = NumberFormat.getCurrencyInstance().format(total).formatDouble(),
                color = White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ActionButton(
                text = stringResource(id = R.string.discard),
                onClick = {
                    option = 0
                    isDialogVisible = true
                },
                modifier = Modifier.weight(1f)
            )

            ActionButton(
                text = stringResource(id = R.string.order),
                onClick = {
                    option = 1
                    isDialogVisible = true
                },
                modifier = Modifier.weight(1f)
            )
        }
    }

    if (isSheetOpened) {
        BottomSheet(sharedViewModel = sharedViewModel, onDismiss = { isSheetOpened = it })
    }
}