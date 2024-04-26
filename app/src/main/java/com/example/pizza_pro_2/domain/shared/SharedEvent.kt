package com.example.pizza_pro_2.domain.shared

import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.models.Pizza

sealed class SharedEvent {
    data class SearchQueryChanged(val query: String) : SharedEvent()
    data class PizzaCountChanged(val pizza: Pizza) : SharedEvent()
    data class PizzaSelectionChanged(val pizza: Pizza) : SharedEvent()
    data object PlaceOrder : SharedEvent()
    data class CancelOrder(val order: Order) : SharedEvent()
    data object DiscardOrder : SharedEvent()
}