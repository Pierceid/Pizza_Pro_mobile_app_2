package com.example.pizza_pro_2.domain.sheet

import com.example.pizza_pro_2.models.Pizza

sealed class SheetEvent {
    data class PizzaSelectionChanged(val pizza: Pizza) : SheetEvent()
    data class ExpansionChanged(val isExpanded: Boolean) : SheetEvent()
}