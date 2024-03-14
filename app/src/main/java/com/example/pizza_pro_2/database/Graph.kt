package com.example.pizza_pro_2.database

import android.content.Context

object Graph {
    lateinit var database: MyDatabase
        private set

    val repository by lazy { MyRepository(database.dao) }

    fun provide(context:Context) {
        database = MyDatabase.getDatabase(context)
    }
}