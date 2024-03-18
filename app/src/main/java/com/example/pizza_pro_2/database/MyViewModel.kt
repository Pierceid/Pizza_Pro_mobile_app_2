package com.example.pizza_pro_2.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MyViewModel(private val repository: MyRepository) : ViewModel() {
    var users: Flow<MutableList<User>>
    var orders: Flow<MutableList<Order>>
    var user: Flow<User?>

    init {
        orders = repository.allOrders
        users = repository.allUsers
        user = flow { }
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun addOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOrder(order)
        }
    }

    fun removeUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeUser(user)
        }
    }

    fun removeOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeOrder(order)
        }
    }

    fun clearAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearAllUsers()
        }
    }

    fun clearAllOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearAllOrders()
        }
    }

    fun getUser(name: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            user = repository.getUser(name, email)
        }
    }

    fun getFilteredUsers(regex: String) {
        users = repository.getFilteredUsers(regex)
    }

    fun getFilteredOrders(regex: String) {
        orders = repository.getFilteredOrders(regex)
    }
}