package com.example.pizza_pro_2.domain.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.data.DataSource
import com.example.pizza_pro_2.database.MyRepository
import com.example.pizza_pro_2.models.Pizza
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SharedViewModel(val myRepository: MyRepository) : ViewModel() {
    private val _state = MutableStateFlow(SharedFormState(allPizzas = DataSource().loadData()))
    val state = _state.asStateFlow()

    /*
    TODO: this is for history screen => State(listOfUsers, listOfOrders)
    val _state: StateFlow<SharedFormState> = myRepository.getAllUsers().map { SharedFormState(it) }.
        stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SharedFormState()
        )
     */

    init {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(filteredPizzas = currentState.allPizzas)
            }
        }
    }

    fun onEvent(event: SharedFormEvent) {
        viewModelScope.launch {
            when (event) {
                is SharedFormEvent.CurrentUserChanged -> {
                    _state.update { currentState ->
                        currentState.copy(currentUser = event.user)
                    }
                }

                is SharedFormEvent.SearchQueryChanged -> {
                    filterPizzas(query = event.query)
                }

                is SharedFormEvent.PizzaCountChanged -> {
                    updatePizzaCount(pizza = event.pizza)
                }

                is SharedFormEvent.PizzaSelectionChanged -> {
                    _state.update { currentState ->
                        currentState.copy(selectedPizza = event.pizza)
                    }
                }

                is SharedFormEvent.SignUp -> {
                    _state.update { currentState ->
                        currentState.copy(currentUser = event.user)
                    }
                    myRepository.insertUser(user = event.user)
                }

                is SharedFormEvent.SignIn -> {
                    _state.update { currentState ->
                        currentState.copy(currentUser = event.user)
                    }
                }

                is SharedFormEvent.PlaceOrder -> {
                    myRepository.insertOrder(order = event.order)
                }

                is SharedFormEvent.CancelOrder -> {
                    myRepository.deleteOrder(order = event.order)
                }

                is SharedFormEvent.DiscardOrder -> {
                    clearPizzas()
                }
            }
        }
    }

    private fun updatePizzaCount(pizza: Pizza) {
        val updatedList = _state.value.allPizzas
        updatedList.first { it.id == pizza.id }.count = pizza.count
        val orderedList = updatedList.filter { it.count > 0 }
        _state.update { currentState ->
            currentState.copy(allPizzas = updatedList, orderedPizzas = orderedList)
        }
        updateCost()
    }

    private fun filterPizzas(query: String = _state.value.searchQuery.lowercase()) {
        val filteredList = _state.value.allPizzas.filter { it.name!!.lowercase().contains(query) }
        _state.update { currentState ->
            currentState.copy(searchQuery = query, filteredPizzas = filteredList)
        }
    }

    private fun clearPizzas() {
        val clearedList = _state.value.allPizzas.map { it.copy(count = 0) }
        _state.update { currentState ->
            currentState.copy(allPizzas = clearedList, orderedPizzas = emptyList())
        }
        updateCost()
    }

    private fun updateCost() {
        val sum = _state.value.orderedPizzas.sumOf { it.count * it.cost }
        _state.update { currentState ->
            currentState.copy(itemsCost = sum)
        }
    }
}
