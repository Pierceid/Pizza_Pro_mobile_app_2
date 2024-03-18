package com.example.pizza_pro_2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.User

@Database(entities = [User::class, Order::class], version = 3, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract val dao: MyDao

    companion object {

        @Volatile
        private var INSTANCE: MyDatabase? = null


        fun getDatabase(context: Context): MyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "my_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}