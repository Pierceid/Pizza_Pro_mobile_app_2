package com.example.pizza_pro_2.database

import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MyRepository(private val myDao: MyDao) {
    var currentUser : Flow<User?> = flowOf(null)
    val allUsers: Flow<List<User>> = myDao.getUsers()
    val allOrders: Flow<List<Order>> = myDao.getOrders()

    suspend fun insertUser(user: User) = myDao.insertUser(user)

    suspend fun updateUser(user: User) = myDao.updateUser(user)

    suspend fun deleteUser(user: User) = myDao.deleteUser(user)

    suspend fun insertOrder(order: Order) = myDao.insertOrder(order)

    suspend fun deleteOrder(order: Order) = myDao.deleteOrder(order)

    suspend fun deleteAllUsers() = myDao.deleteAllUsers()

    suspend fun deleteAllOrders() = myDao.deleteAllOrders()

    fun setCurrentUser(id: Int = -1, name: String = "", email: String = "") {
        currentUser = myDao.getUser(id, name, email)
    }

    fun getUsers(regex: String = ""): Flow<List<User>> = myDao.getUsers(regex)

    fun getOrders(regex: String = ""): Flow<List<Order>> = myDao.getOrders(regex)
}