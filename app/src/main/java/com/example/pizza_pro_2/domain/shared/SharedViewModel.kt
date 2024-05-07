package com.example.pizza_pro_2.domain.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.data.DataSource
import com.example.pizza_pro_2.database.MyRepository
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.models.Pizza
import com.example.pizza_pro_2.options.PizzaSortType
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
                is SharedEvent.FilterPizzas -> {
                    filterPizzas(event.sortType, event.query)
                }

                is SharedEvent.PizzaSelectionChanged -> {
                    _state.update {
                        it.copy(selectedPizza = event.pizza)
                    }
                }

                is SharedEvent.PizzaCountChanged -> {
                    updatePizzaCount(pizza = event.pizza)
                }

                is SharedEvent.DialogVisibilityChanged -> {
                    _state.update {
                        it.copy(isDialogVisible = event.isVisible)
                    }
                }

                is SharedEvent.PlaceOrder -> {
                    val order = Order(
                        user = myRepository.currentUser.firstOrNull()!!.id,
                        time = System.currentTimeMillis(),
                        place = event.place,
                        items = _state.value.orderedPizzas.sumOf { it.count },
                        cost = event.total,
                        payment = event.payment.toString()
                    )
                    myRepository.insertOrder(order)
                    clearPizzas()
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
    }

    private fun filterPizzas(sortType: PizzaSortType, query: String) {
        val updatedList = _state.value.allPizzas.filter {
            it.name!!.lowercase().contains(query.lowercase())
        }.toMutableList()

        when (sortType) {
            PizzaSortType.NAME -> updatedList.sortBy { it.name }
            PizzaSortType.RATING -> updatedList.sortByDescending { it.rating }
            PizzaSortType.PRICE -> updatedList.sortBy { it.cost }
        }

        _state.update {
            it.copy(filteredPizzas = updatedList.toList())
        }
    }

    private fun clearPizzas() {
        val clearedList = _state.value.allPizzas.map { it.copy(count = 0) }
        _state.update {
            it.copy(allPizzas = clearedList, orderedPizzas = emptyList())
        }
    }
}
