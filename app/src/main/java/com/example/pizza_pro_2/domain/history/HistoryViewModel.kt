package com.example.pizza_pro_2.domain.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.database.MyRepository
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.options.SortType
import com.example.pizza_pro_2.options.TableType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModel(private val myRepository: MyRepository) : ViewModel() {
    private val _tableType = MutableStateFlow(TableType.ORDERS)
    private val _sortType = MutableStateFlow(SortType.TIME)
    private val _searchQuery = MutableStateFlow("")
    private val _items = combine(_searchQuery, _tableType, _sortType) { query, tableType, sortType ->
        Triple(query, tableType, sortType)
    }.flatMapLatest { (query, tableType, sortType) ->
        when (tableType) {
            TableType.USERS -> myRepository.getUsers(query)
            TableType.ORDERS -> myRepository.getOrders(myRepository.currentUser.firstOrNull()?.name ?: "", sortType)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(HistoryState())
    val state = combine(
        _state, _tableType, _sortType, _searchQuery, _items
    ) { state, tableType, sortType, searchQuery, items ->
        state.copy(
            tableType = tableType,
            sortType = sortType,
            searchQuery = searchQuery,
            orders = items.filterIsInstance<Order>(),
            users = items.filterIsInstance<User>()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HistoryState())

    fun onEvent(event: HistoryEvent) {
        viewModelScope.launch {
            when (event) {
                is HistoryEvent.TableTypeChanged -> {
                    _tableType.value = event.type
                }
                is HistoryEvent.SortTypeChanged -> {
                    _sortType.value = event.type
                }
                is HistoryEvent.SearchQueryChanged -> {
                    _searchQuery.value = event.query
                }
                is HistoryEvent.ItemSelectionChanged -> {
                    _state.update {
                        it.copy(selectedItem = event.item)
                    }
                }
                is HistoryEvent.Remove -> {
                    _state.value.selectedItem?.let {
                        when (_tableType.value) {
                            TableType.ORDERS -> myRepository.deleteOrder(it as Order)
                            TableType.USERS -> myRepository.deleteUser(it as User)
                        }
                    }
                }
                is HistoryEvent.Clear -> {
                    when (_tableType.value) {
                        TableType.ORDERS -> myRepository.deleteAllOrders()
                        TableType.USERS -> myRepository.deleteAllUsers()
                    }
                }
            }
        }
    }
}
