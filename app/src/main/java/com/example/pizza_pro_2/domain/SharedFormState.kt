package com.example.pizza_pro_2.domain

import com.example.pizza_pro_2.models.Pizza

data class SharedFormState(
    val searchQuery: String = "",
    val selectedPizza: Pizza? = null,
    val allPizzas: List<Pizza> = emptyList(),
    val filteredPizzas: List<Pizza> = emptyList(),
    val orderedPizzas: List<Pizza> = emptyList()
)