package com.example.pizza_pro_2.domain.history

import com.example.pizza_pro_2.options.OrderSortType
import com.example.pizza_pro_2.options.ReviewSortType
import com.example.pizza_pro_2.options.TableType

sealed class HistoryEvent {
    data class TableTypeChanged(val type: TableType) : HistoryEvent()
    data class OrderSortTypeChanged(val type: OrderSortType) : HistoryEvent()
    data class ReviewSortTypeChanged(val type: ReviewSortType) : HistoryEvent()
    data class DialogVisibilityChanged(val isVisible: Boolean) : HistoryEvent()
    data class ItemSelectionChanged(val item: Any) : HistoryEvent()
    data class OptionChanged(val option: Int): HistoryEvent()
    data object Remove : HistoryEvent()
    data object Clear : HistoryEvent()
}