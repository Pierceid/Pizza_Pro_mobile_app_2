package com.example.pizza_pro_2.domain

import com.example.pizza_pro_2.models.Pizza

sealed class SharedFormEvent {
    data class OnSearchQueryChange(val query: String): SharedFormEvent()
    data class OnPizzaCountChange(val pizza: Pizza): SharedFormEvent()
    data class OnPizzaSelectionChange(val pizza: Pizza): SharedFormEvent()
    data object Refresh: SharedFormEvent()
}