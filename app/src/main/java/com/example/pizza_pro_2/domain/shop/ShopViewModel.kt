package com.example.pizza_pro_2.domain.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShopViewModel : ViewModel() {
    private val _state = MutableStateFlow(ShopState())
    val state = _state.asStateFlow()

    fun onEvent(event: ShopEvent) {
        viewModelScope.launch {
            when (event) {
                is ShopEvent.SortTypeChanged -> {
                    _state.update {
                        it.copy(sortType = event.type)
                    }
                }

                is ShopEvent.SearchQueryChanged -> {
                    _state.update {
                        it.copy(searchQuery = event.query)
                    }
                }
            }
        }
    }
}
