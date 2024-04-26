package com.example.pizza_pro_2.domain.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.data.DataSource
import com.example.pizza_pro_2.database.MyRepository
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.models.Pizza
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SharedViewModel(private val myRepository: MyRepository) : ViewModel() {
    private val _state = MutableStateFlow(SharedState(allPizzas = DataSource().loadData()))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(filteredPizzas = it.allPizzas)
            }
        }
    }

    fun onEvent(event: SharedEvent) {
        viewModelScope.launch {
            when (event) {
                is SharedEvent.SearchQueryChanged -> {
                    filterPizzas(query = event.query)
                }

                is SharedEvent.PizzaCountChanged -> {
                    updatePizzaCount(pizza = event.pizza)
                }

                is SharedEvent.PizzaSelectionChanged -> {
                    _state.update {
                        it.copy(selectedPizza = event.pizza)
                    }
                }

                is SharedEvent.PlaceOrder -> {
                    val order = Order(
                        name = myRepository.currentUser.firstOrNull()!!.name,
                        time = System.currentTimeMillis(),
                        place = "abc",
                        items = _state.value.orderedPizzas.sumOf { it.count },
                        cost = _state.value.orderedPizzas.sumOf { it.count * it.cost }
                    )
                    myRepository.insertOrder(order)
                    clearPizzas()
                }

                is SharedEvent.CancelOrder -> {
                    myRepository.deleteOrder(event.order)
                }

                is SharedEvent.DiscardOrder -> {
                    clearPizzas()
                }
            }
        }
    }

    private fun updatePizzaCount(pizza: Pizza) {
        val updatedList = _state.value.allPizzas
        updatedList.first { it.id == pizza.id }.count = pizza.count
        val orderedList = updatedList.filter { it.count > 0 }
        _state.update {
            it.copy(allPizzas = updatedList, orderedPizzas = orderedList)
        }
        updateCost()
    }

    private fun filterPizzas(query: String = _state.value.searchQuery.lowercase()) {
        val filteredList = _state.value.allPizzas.filter { it.name!!.lowercase().contains(query) }
        _state.update {
            it.copy(searchQuery = query, filteredPizzas = filteredList)
        }
    }

    private fun clearPizzas() {
        val clearedList = _state.value.allPizzas.map { it.copy(count = 0) }
        _state.update {
            it.copy(allPizzas = clearedList, orderedPizzas = emptyList())
        }
        updateCost()
    }

    private fun updateCost() {
        val sum = _state.value.orderedPizzas.sumOf { it.count * it.cost }
        _state.update {
            it.copy(itemsCost = sum)
        }
    }
}
