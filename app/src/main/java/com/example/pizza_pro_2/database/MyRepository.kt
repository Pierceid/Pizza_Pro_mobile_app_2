package com.example.pizza_pro_2.database

import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MyRepository(private val myDao: MyDao) {
    var currentUser : Flow<User?> = flowOf(null)
    val allUsers: Flow<List<User>> = myDao.getAllUsers()
    val allOrders: Flow<List<Order>> = myDao.getAllOrders()

    suspend fun insertUser(user: User) = myDao.insertUser(user)

    suspend fun updateUser(user: User) = myDao.updateUser(user)

    suspend fun deleteUser(user: User) = myDao.deleteUser(user)

    suspend fun insertOrder(order: Order) = myDao.insertOrder(order)

    suspend fun updateOrder(order: Order) = myDao.updateOrder(order)

    suspend fun deleteOrder(order: Order) = myDao.deleteOrder(order)

    fun setCurrentUser(id: Int = -1, name: String = "", email: String = "") {
        currentUser = myDao.getUser(id, name, email)
    }

    fun getFilteredUsers(regex: String = ""): Flow<List<User>> = myDao.getFilteredUsers(regex)

    fun getFilteredOrders(regex: String = ""): Flow<List<Order>> = myDao.getFilteredOrders(regex)
}