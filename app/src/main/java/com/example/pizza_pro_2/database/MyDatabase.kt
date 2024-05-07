package com.example.pizza_pro_2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.User

@Database(entities = [User::class, Order::class], version = 2, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract val myDao: MyDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = MyDatabase::class.java,
                    name = "my_database"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}