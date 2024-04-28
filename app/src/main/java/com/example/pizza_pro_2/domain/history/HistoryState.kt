package com.example.pizza_pro_2.domain.history

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.options.OrderSortType
import com.example.pizza_pro_2.options.TableType
import com.example.pizza_pro_2.ui.theme.Slate

@Stable
data class HistoryState(
    val tableType: TableType = TableType.ORDERS,
    val orderSortType: OrderSortType = OrderSortType.TIME,
    val searchQuery: String = "",
    val isDialogVisible: Boolean = false,
    val orders: List<Order> = emptyList(),
    val users: List<User> = emptyList(),
    val selectedItem: Any? = null,
    val headerId: Int = R.string.your_orders,
    val switchToTable: TableType = TableType.USERS,
    val buttonOption: Int = -1,
    val dialogTitleId: Int = -1,
    val dialogTextId: Int = -1,
    val toastMessageId: Int = -1,
    val dialogEvent: HistoryEvent? = null,
    val dialogColor: Color = Slate
)