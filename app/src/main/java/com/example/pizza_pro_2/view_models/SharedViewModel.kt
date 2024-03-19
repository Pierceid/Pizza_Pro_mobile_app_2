package com.example.pizza_pro_2.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.pizza_pro_2.data.DataSource
import com.example.pizza_pro_2.domain.SharedFormEvent
import com.example.pizza_pro_2.domain.SharedFormState
import com.example.pizza_pro_2.models.Pizza

class SharedViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    var state by mutableStateOf(
        SharedFormState(
            searchQuery = savedStateHandle.get<String>("search_query") ?: "",
            selectedPizza = savedStateHandle.get<Pizza>("selected_pizza"),
            allPizzas = savedStateHandle.get<List<Pizza>>("all_pizzas") ?: DataSource().loadData(),
            filteredPizzas = savedStateHandle.get<List<Pizza>>("filtered_pizzas") ?: emptyList(),
            orderedPizzas = savedStateHandle.get<List<Pizza>>("ordered_pizzas") ?: emptyList()
        )
    )
        private set

    fun onEvent(event: SharedFormEvent) {
        when (event) {
            is SharedFormEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                filterPizzas()
            }

            is SharedFormEvent.OnPizzaCountChange -> {
                updatePizzaCount(pizza = event.pizza)
            }

            is SharedFormEvent.OnPizzaSelectionChange -> {
                selectPizza(pizza = event.pizza)
            }

            is SharedFormEvent.Refresh -> {

            }
        }
    }

    private fun selectPizza(pizza: Pizza) {
        updateState(state.copy(selectedPizza = pizza))
    }

    private fun updatePizzaCount(pizza: Pizza) {
        val updatedList = state.allPizzas
        updatedList.first { it.id == pizza.id }.count = pizza.count
        val orderedList = state.allPizzas.filter { it.count > 0 }
        updateState(state.copy(allPizzas = updatedList, orderedPizzas = orderedList))
    }

    private fun filterPizzas(query: String = state.searchQuery.lowercase()) {
        val filteredList = state.allPizzas.filter { it.name!!.lowercase().contains(query) }
        updateState(state.copy(filteredPizzas = filteredList))
    }

    private fun updateState(newState: SharedFormState) {
        state = newState
        savedStateHandle["search_query"] = newState.searchQuery
        savedStateHandle["selected_pizza"] = newState.selectedPizza
        savedStateHandle["all_pizzas"] = newState.allPizzas
        savedStateHandle["filtered_pizzas"] = newState.filteredPizzas
        savedStateHandle["ordered_pizzas"] = newState.orderedPizzas
    }
}
