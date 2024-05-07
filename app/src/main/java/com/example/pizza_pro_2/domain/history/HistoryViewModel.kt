package com.example.pizza_pro_2.domain.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.database.MyRepository
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.options.OrderSortType
import com.example.pizza_pro_2.options.TableType
import com.example.pizza_pro_2.ui.theme.Maroon
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModel(private val myRepository: MyRepository) : ViewModel() {
    private val _tableType = MutableStateFlow(TableType.ORDERS)
    private val _sortType = MutableStateFlow(OrderSortType.TIME)
    private val _searchQuery = MutableStateFlow("")
    private val _items =
        combine(_searchQuery, _tableType, _sortType) { query, tableType, sortType ->
            Triple(query, tableType, sortType)
        }.flatMapLatest { (query, tableType, sortType) ->
            when (tableType) {
                TableType.USERS -> myRepository.getUsers(query)
                TableType.ORDERS -> myRepository.getOrders(
                    myRepository.currentUser.firstOrNull()!!.id, sortType
                )
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(HistoryState())
    val state = combine(
        _state, _tableType, _sortType, _searchQuery, _items
    ) { state, tableType, sortType, searchQuery, items ->
        state.copy(
            tableType = tableType,
            orderSortType = sortType,
            searchQuery = searchQuery,
            headerId = when (tableType) {
                TableType.ORDERS -> R.string.your_orders
                TableType.USERS -> R.string.active_accounts
            },
            switchToTable = when (tableType) {
                TableType.ORDERS -> TableType.USERS
                TableType.USERS -> TableType.ORDERS
            },
            orders = items.filterIsInstance<Order>(),
            users = items.filterIsInstance<User>()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HistoryState())

    fun onEvent(event: HistoryEvent) {
        viewModelScope.launch {
            when (event) {
                is HistoryEvent.TableTypeChanged -> {
                    _tableType.value = event.type
                }

                is HistoryEvent.SortTypeChanged -> {
                    _sortType.value = event.type
                }

                is HistoryEvent.SearchQueryChanged -> {
                    _searchQuery.value = event.query
                }

                is HistoryEvent.DialogVisibilityChanged -> {
                    _state.update {
                        it.copy(isDialogVisible = event.isVisible)
                    }
                }

                is HistoryEvent.ItemSelectionChanged -> {
                    _state.update {
                        it.copy(selectedItem = event.item)
                    }
                }

                is HistoryEvent.OptionChanged -> {
                    _state.update {
                        it.copy(buttonOption = event.option, isDialogVisible = true)
                    }
                    when (event.option) {
                        0 -> {
                            _state.update {
                                it.copy(
                                    dialogTitleId = R.string.cancel_order,
                                    dialogTextId = R.string.are_you_sure_you_want_to_cancel_this_order,
                                    toastMessageId = R.string.order_cancelled_successfully,
                                    dialogEvent = HistoryEvent.Remove,
                                    dialogColor = Maroon
                                )
                            }
                        }

                        1 -> {
                            _state.update {
                                it.copy(
                                    dialogTitleId = R.string.remove_account,
                                    dialogTextId = R.string.are_you_sure_you_want_to_remove_this_account,
                                    toastMessageId = R.string.user_removed_successfully,
                                    dialogEvent = HistoryEvent.Remove,
                                    dialogColor = Maroon
                                )
                            }
                        }

                        2 -> {
                            _state.update {
                                it.copy(
                                    dialogTitleId = R.string.clear_history,
                                    dialogTextId = R.string.are_you_certain_you_want_to_proceed_with_cancelling_all_of_your_orders,
                                    toastMessageId = R.string.history_cleared_successfully,
                                    dialogEvent = HistoryEvent.Clear,
                                    dialogColor = Maroon
                                )
                            }
                        }

                        3 -> {
                            _state.update {
                                it.copy(
                                    dialogTitleId = R.string.clear_history,
                                    dialogTextId = R.string.are_you_certain_you_want_to_proceed_with_removing_all_active_accounts,
                                    toastMessageId = R.string.history_cleared_successfully,
                                    dialogEvent = HistoryEvent.Clear,
                                    dialogColor = Maroon
                                )
                            }
                        }

                        else -> {}
                    }
                }

                is HistoryEvent.Remove -> {
                    _state.value.selectedItem?.let { item ->
                        when (_tableType.value) {
                            TableType.ORDERS -> (item as? Order)?.let {
                                myRepository.deleteOrder(it)
                            }

                            TableType.USERS -> (item as? User)?.let {
                                val currentId = myRepository.currentUser.firstOrNull()!!.id
                                myRepository.deleteUser(it)
                                if (it.id == currentId) {
                                    exitProcess(0)
                                }
                            }
                        }
                    }
                }

                is HistoryEvent.Clear -> {
                    myRepository.currentUser.firstOrNull()?.let {
                        when (_tableType.value) {
                            TableType.ORDERS -> {
                                myRepository.deleteUsersOrders(it.id)
                            }

                            TableType.USERS -> {
                                myRepository.deleteAllUsers()
                                exitProcess(0)
                            }
                        }
                    }
                }
            }
        }
    }
}
