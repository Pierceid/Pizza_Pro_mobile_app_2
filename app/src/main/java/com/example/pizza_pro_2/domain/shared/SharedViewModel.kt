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

class SharedViewModel(private val myRepository: MyRepository) : ViewModel() {
    private val _state = MutableStateFlow(SharedState(allPizzas = DataSource().loadData()))
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

    fun onEvent(event: SharedEvent) {
        viewModelScope.launch {
            when (event) {
                is SharedEvent.CurrentUserChanged -> {
                    _state.update { currentState ->
                        currentState.copy(currentUser = event.user)
                    }
                }

                is SharedEvent.SearchQueryChanged -> {
                    filterPizzas(query = event.query)
                }

                is SharedEvent.PizzaCountChanged -> {
                    updatePizzaCount(pizza = event.pizza)
                }

                is SharedEvent.PizzaSelectionChanged -> {
                    _state.update { currentState ->
                        currentState.copy(selectedPizza = event.pizza)
                    }
                }

                is SharedEvent.SignUp -> {
                    myRepository.insertUser(user = event.user)
                    _state.update { currentState ->
                        currentState.copy(currentUser = event.user)
                    }
                }

                is SharedEvent.SignIn -> {
                    myRepository.getUser(name = event.name, email = event.email).collect { user ->
                        _state.update { currentState ->
                            currentState.copy(currentUser = user)
                        }
                    }
                }

                is SharedEvent.UpdateAccount -> {
                    var userToUpdate = _state.value.currentUser!!
                    myRepository.getUser(name = userToUpdate.name, email = userToUpdate.email)
                        .collect { user ->
                            if (user != null) {
                                userToUpdate = user.copy(
                                    name = event.name,
                                    email = event.email,
                                    password = event.password,
                                    gender = event.gender,
                                    id = user.id
                                )
                                myRepository.updateUser(user = userToUpdate)

                                _state.update { currentState ->
                                    currentState.copy(currentUser = userToUpdate)
                                }
                            }
                        }
                }

                is SharedEvent.DeleteAccount -> {
                    val userToDelete = _state.value.currentUser!!
                    myRepository.getUser(name = userToDelete.name, email = userToDelete.email)
                        .collect { user ->
                            myRepository.deleteUser(user = user!!)
                        }
                }

                is SharedEvent.PlaceOrder -> {
                    myRepository.insertOrder(order = event.order)
                }

                is SharedEvent.CancelOrder -> {
                    myRepository.deleteOrder(order = event.order)
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
