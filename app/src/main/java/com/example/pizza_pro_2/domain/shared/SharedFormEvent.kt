package com.example.pizza_pro_2.domain.shared

import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.models.Pizza

sealed class SharedFormEvent {
    data class CurrentUserChanged(val user: User?): SharedFormEvent()
    data class SearchQueryChanged(val query: String): SharedFormEvent()
    data class PizzaCountChanged(val pizza: Pizza): SharedFormEvent()
    data class PizzaSelectionChanged(val pizza: Pizza): SharedFormEvent()
    data object Discard: SharedFormEvent()
    data object Order: SharedFormEvent()
}