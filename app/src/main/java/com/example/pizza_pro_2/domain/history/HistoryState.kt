package com.example.pizza_pro_2.domain.history

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.Review
import com.example.pizza_pro_2.options.OrderSortType
import com.example.pizza_pro_2.options.ReviewSortType
import com.example.pizza_pro_2.options.TableType
import com.example.pizza_pro_2.ui.theme.Slate

@Stable
data class HistoryState(
    val tableType: TableType = TableType.ORDERS,
    val orderSortType: OrderSortType = OrderSortType.TIME,
    val reviewSortType: ReviewSortType = ReviewSortType.TIME,
    val isDialogVisible: Boolean = false,
    val orders: List<Order> = emptyList(),
    val reviews: List<Review> = emptyList(),
    val selectedItem: Any? = null,
    val headerId: Int = R.string.your_orders,
    val switchToTable: TableType = TableType.REVIEWS,
    val buttonOption: Int = -1,
    val dialogTitleId: Int = R.string.empty,
    val dialogTextId: Int = R.string.empty,
    val toastMessageId: Int = R.string.empty,
    val dialogEvent: HistoryEvent? = null,
    val dialogColor: Color = Slate
)