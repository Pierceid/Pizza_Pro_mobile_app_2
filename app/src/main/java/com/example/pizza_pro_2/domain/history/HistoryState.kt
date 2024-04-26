package com.example.pizza_pro_2.domain.history

import androidx.compose.runtime.Stable
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.options.SortType
import com.example.pizza_pro_2.options.TableType

@Stable
data class HistoryState(
    val tableType: TableType = TableType.ORDERS,
    val sortType: SortType = SortType.TIME,
    val searchQuery: String = "",
    val orders: List<Order> = emptyList(),
    val users: List<User> = emptyList(),
    val selectedItem: Any? = null
)