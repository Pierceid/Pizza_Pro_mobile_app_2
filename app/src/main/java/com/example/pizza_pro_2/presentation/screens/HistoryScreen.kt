package com.example.pizza_pro_2.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun HistoryScreen(
    navController: NavController,
    sharedState: SharedState,
    onSharedEvent: (SharedEvent) -> Unit
) {
    val viewModel: HistoryViewModel = viewModel(factory = MyViewModelProvider.factory)
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val sortTypes = listOf(OrderSortType.TIME, OrderSortType.PLACE, OrderSortType.PURCHASE)

    DefaultColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (state.isDialogVisible) {
            val message = stringResource(state.toastMessageId)

            InfoDialog(
                titleId = state.dialogTitleId,
                textId = state.dialogTextId,
                onDismiss = {
                    viewModel.onEvent(HistoryEvent.DialogVisibilityChanged(false))
                },
                dismissButton = R.string.no,
                onConfirm = {
                    state.dialogEvent?.let {
                        viewModel.onEvent(it)
                    }
                    viewModel.onEvent(HistoryEvent.DialogVisibilityChanged(false))
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                },
                confirmButton = R.string.yes,
                color = state.dialogColor
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderText(textId = state.headerId, textStyle = MaterialTheme.typography.headlineMedium)

            IconButton(
                onClick = {
                    viewModel.onEvent(HistoryEvent.TableTypeChanged(state.switchToTable))
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
                    options = sortTypes,
                    type = 1
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
                                viewModel.onEvent(HistoryEvent.OptionChanged(0))
                                viewModel.onEvent(HistoryEvent.ItemSelectionChanged(order))
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
                                viewModel.onEvent(HistoryEvent.OptionChanged(1))
                                viewModel.onEvent(HistoryEvent.ItemSelectionChanged(user))
                            }
                        )
                    }
                }
            }
        }

        ActionButton(
            textId = R.string.clear,
            onClick = {
                viewModel.onEvent(
                    HistoryEvent.OptionChanged(
                        when (state.tableType) {
                            TableType.ORDERS -> 2
                            TableType.USERS -> 3
                        }
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
