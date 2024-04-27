package com.example.pizza_pro_2.domain.history

import com.example.pizza_pro_2.options.OrderSortType
import com.example.pizza_pro_2.options.TableType

sealed class HistoryEvent {
    data class TableTypeChanged(val type: TableType) : HistoryEvent()
    data class SortTypeChanged(val type: OrderSortType) : HistoryEvent()
    data class SearchQueryChanged(val query: String) : HistoryEvent()
    data class DialogVisibilityChanged(val isVisible: Boolean) : HistoryEvent()
    data class ItemSelectionChanged(val item: Any) : HistoryEvent()
    data object Remove : HistoryEvent()
    data object Clear : HistoryEvent()
}