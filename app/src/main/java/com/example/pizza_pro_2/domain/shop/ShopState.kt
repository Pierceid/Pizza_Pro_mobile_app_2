package com.example.pizza_pro_2.domain.shop

import androidx.compose.runtime.Stable
import com.example.pizza_pro_2.options.PizzaSortType

@Stable
data class ShopState(
    val sortType: PizzaSortType = PizzaSortType.NAME,
    val searchQuery: String = ""
)