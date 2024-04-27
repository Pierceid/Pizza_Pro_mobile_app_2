package com.example.pizza_pro_2.domain.sheet

import com.example.pizza_pro_2.models.Pizza

data class SheetState(
    val pizza: Pizza? = null,
    val isExpanded: Boolean = false
)
