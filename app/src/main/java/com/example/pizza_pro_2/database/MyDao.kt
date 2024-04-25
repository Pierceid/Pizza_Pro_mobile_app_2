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

    @Update
    suspend fun updateOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Transaction
    @Query("SELECT * FROM users WHERE id = :id OR name = :name OR email = :email LIMIT 1")
    fun getUser(id: Int = -1, name: String = "", email: String = ""): Flow<User?>

    @Transaction
    @Query("SELECT * FROM users WHERE name LIKE '%' || :regex || '%' ORDER BY id DESC")
    fun getFilteredUsers(regex: String = ""): Flow<List<User>>

    @Transaction
    @Query("SELECT * FROM orders WHERE name LIKE '%' || :regex || '%' ORDER BY id DESC")
    fun getFilteredOrders(regex: String = ""): Flow<List<Order>>

    @Transaction
    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAllUsers(): Flow<List<User>>

    @Transaction
    @Query("SELECT * FROM orders ORDER BY id DESC")
    fun getAllOrders(): Flow<List<Order>>
}