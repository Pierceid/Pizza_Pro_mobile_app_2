package com.example.pizza_pro_2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Transaction
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Transaction
    @Query("DELETE FROM orders WHERE user = :user")
    suspend fun deleteUsersOrders(user: Int)

    @Transaction
    @Query("SELECT * FROM users WHERE id = :id OR name = :name OR email = :email LIMIT 1")
    fun getUser(id: Int = -1, name: String = "", email: String = ""): Flow<User?>

    @Transaction
    @Query("SELECT * FROM users WHERE name LIKE '%' || :regex || '%' ORDER BY id ASC")
    fun getUsers(regex: String = ""): Flow<List<User>>

    @Transaction
    @Query("SELECT * FROM orders WHERE user = :user ORDER BY time DESC")
    fun getOrdersBasedOnTime(user: Int): Flow<List<Order>>

    @Transaction
    @Query("SELECT * FROM orders WHERE user = :user ORDER BY cost ASC")
    fun getOrdersBasedOnCost(user: Int): Flow<List<Order>>

    @Transaction
    @Query("SELECT * FROM orders WHERE user = :user ORDER BY place ASC")
    fun getOrdersBasedOnPlace(user: Int): Flow<List<Order>>
}