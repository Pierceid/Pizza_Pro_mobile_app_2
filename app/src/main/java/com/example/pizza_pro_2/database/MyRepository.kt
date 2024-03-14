package com.example.pizza_pro_2.database

import com.example.pizza_pro_2.database.entity.Order
import com.example.pizza_pro_2.database.entity.User
import kotlinx.coroutines.flow.Flow

class MyRepository(private val dao: MyDao) {

    var allUsers: Flow<MutableList<User>> = dao.getAllUsers()
    var allOrders: Flow<MutableList<Order>> = dao.getAllOrders()

    suspend fun addUser(user: User) = dao.upsertUser(user)
    suspend fun addOrder(order: Order) = dao.upsertOrder(order)
    suspend fun removeUser(user: User) = dao.deleteUser(user)
    suspend fun removeOrder(order: Order) = dao.deleteOrder(order)
    suspend fun clearAllUsers() = dao.clearAllUsers()
    suspend fun clearAllOrders() = dao.clearAllOrders()
    fun getUser(name: String, email: String) = dao.getUser(name, email)
    fun getFilteredOrders(regex: String) = dao.getFilteredOrders(regex)
    fun getFilteredUsers(regex: String) = dao.getFilteredUsers(regex)
}