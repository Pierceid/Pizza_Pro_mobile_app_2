package com.example.pizza_pro_2.domain.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.database.MyRepository
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.Review
import com.example.pizza_pro_2.options.OrderSortType
import com.example.pizza_pro_2.options.ReviewSortType
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

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModel(private val myRepository: MyRepository) : ViewModel() {
    private val _tableType = MutableStateFlow(TableType.ORDERS)
    private val _orderSortType = MutableStateFlow(OrderSortType.TIME)
    private val _reviewSortType = MutableStateFlow(ReviewSortType.TIME)
    private val _items =
        combine(
            _tableType,
            _orderSortType,
            _reviewSortType
        ) { tableType, orderSortType, reviewSortType ->
            Triple(tableType, orderSortType, reviewSortType)
        }.flatMapLatest { (tableType, orderSortType, reviewSortType) ->
            when (tableType) {
                TableType.ORDERS -> myRepository.getOrders(
                    myRepository.currentUser.firstOrNull()!!.id, orderSortType
                )

                TableType.REVIEWS -> myRepository.getReviews(
                    myRepository.currentUser.firstOrNull()!!.id, reviewSortType
                )
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(HistoryState())
    val state = combine(
        _state, _tableType, _orderSortType, _reviewSortType, _items
    ) { state, tableType, orderSortType, reviewSortType, items ->
        state.copy(
            tableType = tableType,
            orderSortType = orderSortType,
            reviewSortType = reviewSortType,
            headerId = when (tableType) {
                TableType.ORDERS -> R.string.your_orders
                TableType.REVIEWS -> R.string.your_reviews
            },
            switchToTable = when (tableType) {
                TableType.ORDERS -> TableType.REVIEWS
                TableType.REVIEWS -> TableType.ORDERS
            },
            orders = items.filterIsInstance<Order>(),
            reviews = items.filterIsInstance<Review>()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HistoryState())

    fun onEvent(event: HistoryEvent) {
        viewModelScope.launch {
            when (event) {
                is HistoryEvent.TableTypeChanged -> {
                    _tableType.value = event.type
                }

                is HistoryEvent.OrderSortTypeChanged -> {
                    _orderSortType.value = event.type
                }

                is HistoryEvent.ReviewSortTypeChanged -> {
                    _reviewSortType.value = event.type
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
                                    dialogTitleId = R.string.cancel_feedback,
                                    dialogTextId = R.string.are_you_sure_you_want_to_cancel_this_review,
                                    toastMessageId = R.string.review_cancelled_successfully,
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
                                    dialogTextId = R.string.are_you_certain_you_want_to_proceed_with_cancelling_all_of_your_reviews,
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

                            TableType.REVIEWS -> (item as? Review)?.let {
                                myRepository.deleteReview(it)
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

                            TableType.REVIEWS -> {
                                myRepository.deleteUsersReviews(it.id)
                            }
                        }
                    }
                }
            }
        }
    }
}
