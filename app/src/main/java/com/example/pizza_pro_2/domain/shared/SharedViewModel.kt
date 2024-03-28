package com.example.pizza_pro_2.domain.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.data.DataSource
import com.example.pizza_pro_2.models.Pizza
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    var state by mutableStateOf(SharedFormState(allPizzas = DataSource().loadData()))

    init {
        updateState(state.copy(filteredPizzas = state.allPizzas))
    }

    fun onEvent(event: SharedFormEvent) {
        viewModelScope.launch {
            when (event) {
                is SharedFormEvent.CurrentUserChanged -> {
                    updateState(state.copy(currentUser = event.user))
                }

                is SharedFormEvent.SearchQueryChanged -> {
                    filterPizzas(query = event.query)
                }

                is SharedFormEvent.PizzaCountChanged -> {
                    updatePizzaCount(pizza = event.pizza)
                }

                is SharedFormEvent.PizzaSelectionChanged -> {
                    updateState(state.copy(selectedPizza = event.pizza))
                }

                is SharedFormEvent.Discard -> {
                    clearPizzas()
                }

                is SharedFormEvent.Order -> {
                    clearPizzas()
                }
            }
        }
    }

    private fun updatePizzaCount(pizza: Pizza) {
        val updatedList = state.allPizzas
        updatedList.first { it.id == pizza.id }.count = pizza.count
        val orderedList = updatedList.filter { it.count > 0 }
        updateState(state.copy(allPizzas = updatedList, orderedPizzas = orderedList))
        updateCost()
    }

    private fun updateCost() {
        val sum = state.orderedPizzas.sumOf { it.count * it.cost }
        updateState(state.copy(itemsCost = sum))
    }

    private fun filterPizzas(query: String = state.searchQuery.lowercase()) {
        val filteredList = state.allPizzas.filter { it.name!!.lowercase().contains(query) }
        updateState(state.copy(searchQuery = query, filteredPizzas = filteredList))
    }

    private fun clearPizzas() {
        val clearedList = state.allPizzas.map { it.copy(count = 0) }
        updateState(state.copy(allPizzas = clearedList, orderedPizzas = emptyList()))
        updateCost()
    }

    private fun updateState(newState: SharedFormState) {
        state = newState
    }
}
