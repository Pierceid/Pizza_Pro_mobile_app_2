package com.example.pizza_pro_2.database

import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.Review
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.options.OrderSortType
import com.example.pizza_pro_2.options.ReviewSortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MyRepository(private val myDao: MyDao) {
    var currentUser: Flow<User?> = flowOf(null)
    val allUsers: Flow<List<User>> = myDao.getUsers()
    
    suspend fun insertUser(user: User) = myDao.insertUser(user)

    suspend fun updateUser(user: User) = myDao.updateUser(user)

    suspend fun deleteUser(user: User) = myDao.deleteUser(user)

    suspend fun insertOrder(order: Order) = myDao.insertOrder(order)

    suspend fun deleteOrder(order: Order) = myDao.deleteOrder(order)

    suspend fun insertReview(review: Review) = myDao.insertReview(review)

    suspend fun deleteReview(review: Review) = myDao.deleteReview(review)

    suspend fun deleteUsersOrders(user: Int) = myDao.deleteUsersOrders(user)

    suspend fun deleteUsersReviews(user: Int) = myDao.deleteUsersReviews(user)

    fun setCurrentUser(id: Int = -1, name: String = "", email: String = "") {
        currentUser = myDao.getUser(id, name, email)
    }

    fun getOrders(user: Int, orderSortType: OrderSortType): Flow<List<Order>> {
        return when (orderSortType) {
            OrderSortType.TIME -> myDao.getOrdersBasedOnTime(user)
            OrderSortType.PURCHASE -> myDao.getOrdersBasedOnCost(user)
        }
    }

    fun getReviews(user: Int, reviewSortType: ReviewSortType): Flow<List<Review>> {
        return when (reviewSortType) {
            ReviewSortType.TIME -> myDao.getReviewsBasedOnTime(user)
            ReviewSortType.JOY -> myDao.getReviewsBasedOnJoy(user)
        }
    }
}
