package com.example.pizza_pro_2.domain.shared

import com.example.pizza_pro_2.models.Pizza
import com.example.pizza_pro_2.options.PizzaSortType

sealed class SharedEvent {
    data class FilterPizzas(val sortType: PizzaSortType, val query: String) : SharedEvent()
    data class PizzaSelectionChanged(val pizza: Pizza?) : SharedEvent()
    data class PizzaCountChanged(val pizza: Pizza) : SharedEvent()
    data class DialogVisibilityChanged(val isVisible: Boolean) : SharedEvent()
    data class PlaceOrder(val place: String, val total: Double): SharedEvent()
    data object DiscardOrder: SharedEvent()
}