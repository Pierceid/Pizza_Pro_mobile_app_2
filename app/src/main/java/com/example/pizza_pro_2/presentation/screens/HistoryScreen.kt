package com.example.pizza_pro_2.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.MyViewModelProvider
import com.example.pizza_pro_2.domain.history.HistoryEvent
import com.example.pizza_pro_2.domain.history.HistoryViewModel
import com.example.pizza_pro_2.domain.shared.SharedEvent
import com.example.pizza_pro_2.domain.shared.SharedState
import com.example.pizza_pro_2.options.OrderSortType
import com.example.pizza_pro_2.options.TableType
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.HeaderText
import com.example.pizza_pro_2.presentation.components.HistoryOrderCard
import com.example.pizza_pro_2.presentation.components.HistoryUserCard
import com.example.pizza_pro_2.presentation.components.InfoDialog
import com.example.pizza_pro_2.presentation.components.InputTextField
import com.example.pizza_pro_2.presentation.components.RadioGroup
import com.example.pizza_pro_2.ui.theme.Maroon

@Composable
fun HistoryScreen(
    navController: NavController,
    sharedState: SharedState,
    onSharedEvent: (SharedEvent) -> Unit
) {
    var option by rememberSaveable { mutableIntStateOf(0) }

    val context = LocalContext.current
    val viewModel: HistoryViewModel = viewModel(factory = MyViewModelProvider.factory)
    val state by viewModel.state.collectAsState()
    val headerId =
        if (state.tableType == TableType.ORDERS) R.string.your_orders
        else R.string.active_accounts
    val switchToTable =
        if (state.tableType == TableType.ORDERS) TableType.USERS
        else TableType.ORDERS
    val dialogTitleId =
        when (option) {
            0 -> R.string.cancel_order
            1 -> R.string.remove_account
            else -> R.string.clear_history
        }
    val dialogTextId =
        when (option) {
            0 -> R.string.are_you_sure_you_want_to_cancel_this_order
            1 -> R.string.are_you_sure_you_want_to_remove_this_account
            2 -> R.string.are_you_certain_you_want_to_proceed_with_cancelling_all_of_your_orders
            else -> R.string.are_you_certain_you_want_to_proceed_with_removing_all_active_accounts
        }
    val toastMessage = stringResource(
        when (option) {
            0 -> R.string.order_cancelled_successfully
            1 -> R.string.user_removed_successfully
            else -> R.string.history_cleared_successfully
        }
    )
    val event = if (option == 0 || option == 1) HistoryEvent.Remove else HistoryEvent.Clear

    DefaultColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        if (state.isDialogVisible) {
            InfoDialog(
                titleId = dialogTitleId,
                textId = dialogTextId,
                onDismiss = {
                    viewModel.onEvent(HistoryEvent.DialogVisibilityChanged(false))
                },
                dismissButton = R.string.no,
                onConfirm = {
                    viewModel.onEvent(event)
                    viewModel.onEvent(HistoryEvent.DialogVisibilityChanged(false))
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                },
                confirmButton = R.string.yes,
                color = Maroon
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderText(textId = headerId, textStyle = MaterialTheme.typography.headlineMedium)

            IconButton(
                onClick = {
                    viewModel.onEvent(HistoryEvent.TableTypeChanged(switchToTable))
                }
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.switcher)
                )
            }
        }

        when (state.tableType) {
            TableType.ORDERS -> {
                RadioGroup(
                    selected = state.orderSortType,
                    onSelectionChange = {
                        viewModel.onEvent(HistoryEvent.SortTypeChanged(it))
                    },
                    options = listOf(OrderSortType.TIME, OrderSortType.PLACE, OrderSortType.PURCHASE),
                    modifier = Modifier.padding(end = 16.dp)
                )
            }

            TableType.USERS -> {
                InputTextField(
                    value = state.searchQuery,
                    onValueChange = {
                        viewModel.onEvent(HistoryEvent.SearchQueryChanged(it))
                    },
                    labelId = R.string.search,
                    leadingIcon = Icons.Default.Search,
                    trailingIcon = Icons.Default.Clear,
                    onTrailingIconClick = {
                        viewModel.onEvent(HistoryEvent.SearchQueryChanged(""))
                    },
                    imeAction = ImeAction.Done
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (state.tableType) {
                TableType.ORDERS -> {
                    items(items = state.orders) { order ->
                        HistoryOrderCard(
                            order = order,
                            onClick = {
                                option = 0
                                viewModel.onEvent(HistoryEvent.ItemSelectionChanged(order))
                                viewModel.onEvent(HistoryEvent.DialogVisibilityChanged(true))
                            },
                            orderSortType = state.orderSortType
                        )
                    }
                }

                TableType.USERS -> {
                    items(items = state.users) { user ->
                        HistoryUserCard(
                            user = user,
                            onClick = {
                                option = 1
                                viewModel.onEvent(HistoryEvent.ItemSelectionChanged(user))
                                viewModel.onEvent(HistoryEvent.DialogVisibilityChanged(true))
                            }
                        )
                    }
                }
            }
        }

        ActionButton(
            textId = R.string.clear,
            onClick = {
                option = when (state.tableType) {
                    TableType.ORDERS -> 2
                    TableType.USERS -> 3
                }
                viewModel.onEvent(HistoryEvent.DialogVisibilityChanged(true))
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
