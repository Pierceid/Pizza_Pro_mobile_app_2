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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.cart.CartEvent
import com.example.pizza_pro_2.domain.cart.CartViewModel
import com.example.pizza_pro_2.domain.shared.SharedEvent
import com.example.pizza_pro_2.domain.shared.SharedState
import com.example.pizza_pro_2.navigation.BottomSheet
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.CartPizzaCard
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.HeaderText
import com.example.pizza_pro_2.presentation.components.InfoDialog
import com.example.pizza_pro_2.ui.theme.Silver
import com.example.pizza_pro_2.ui.theme.White
import com.example.pizza_pro_2.util.Util.Companion.formatPrice

@Composable
fun CartScreen(
    navController: NavController,
    sharedState: SharedState,
    onSharedEvent: (SharedEvent) -> Unit
) {
    val viewModel: CartViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    viewModel.onEvent(CartEvent.CostsChanged(sharedState.orderedPizzas.sumOf { it.count * it.cost }))

    DefaultColumn {
        if (state.isDialogVisible) {
            val message = stringResource(state.toastMessageId)

            InfoDialog(
                titleId = state.dialogTitleId,
                textId = state.dialogTextId,
                onDismiss = {
                    viewModel.onEvent(CartEvent.DialogVisibilityChanged(false))
                },
                dismissButton = R.string.no,
                onConfirm = {
                    if (state.buttonOption == 1) {
                        viewModel.onEvent(CartEvent.SubmitForm)
                    } else {
                        state.dialogEvent?.let(onSharedEvent)
                        viewModel.onEvent(CartEvent.CostsChanged(0.0))
                        viewModel.onEvent(CartEvent.DialogVisibilityChanged(false))
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                },
                confirmButton = R.string.yes,
                isError = !state.isValidForm,
                errorMessageId = state.placeErrorId,
                hasInputField = state.hasDialogInputField,
                inputFieldValue = state.orderPlace,
                onInputFieldValueChange = {
                    viewModel.onEvent(CartEvent.PlaceChanged(it))
                },
                color = state.dialogColor
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = sharedState.orderedPizzas, key = { it.id!! }) { orderedPizza ->
                CartPizzaCard(
                    pizza = orderedPizza,
                    onCountChanged = { pizza ->
                        onSharedEvent(SharedEvent.PizzaCountChanged(pizza))
                        viewModel.onEvent(CartEvent.CostsChanged(
                            sharedState.orderedPizzas.sumOf { it.count * it.cost }
                        ))
                    },
                    onClick = {
                        onSharedEvent(SharedEvent.PizzaSelectionChanged(orderedPizza))
                    }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

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
                text = state.itemsCost.formatPrice(),
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
                text = state.deliveryCost.formatPrice(),
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
                text = state.totalCost.formatPrice(),
                style = MaterialTheme.typography.headlineLarge,
                color = White
            )
        }

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ActionButton(
                textId = R.string.discard,
                onClick = {
                    viewModel.onEvent(CartEvent.OptionChanged(0))
                },
                modifier = Modifier.weight(1f)
            )

            ActionButton(
                textId = R.string.order,
                onClick = {
                    if (sharedState.orderedPizzas.isNotEmpty()) {
                        viewModel.onEvent(CartEvent.OptionChanged(1))
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }

    sharedState.selectedPizza?.let {
        BottomSheet(sharedState.selectedPizza) {
            onSharedEvent(SharedEvent.PizzaSelectionChanged(null))
        }
    }
}
