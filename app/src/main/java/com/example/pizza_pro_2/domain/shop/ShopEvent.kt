package com.example.pizza_pro_2.domain.shop

import com.example.pizza_pro_2.options.PizzaSortType

sealed class ShopEvent {
    data class SortTypeChanged(val type: PizzaSortType) : ShopEvent()
    data class SearchQueryChanged(val query: String) : ShopEvent()
}