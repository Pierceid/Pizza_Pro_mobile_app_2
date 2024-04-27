package com.example.pizza_pro_2.domain.sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.models.Pizza
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SheetViewModel(pizza: Pizza) : ViewModel() {
    private val _state = MutableStateFlow(SheetState(pizza = pizza))
    val state = _state.asStateFlow()

    fun onEvent(event: SheetEvent) {
        viewModelScope.launch {
            when (event) {
                is SheetEvent.PizzaSelectionChanged -> {
                    _state.update {
                        it.copy(pizza = event.pizza)
                    }
                }

                is SheetEvent.ExpansionChanged -> {
                    _state.update {
                        it.copy(isExpanded = event.isExpanded)
                    }
                }
            }
        }
    }
}
