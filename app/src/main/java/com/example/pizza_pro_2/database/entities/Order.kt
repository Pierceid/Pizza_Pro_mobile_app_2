package com.example.pizza_pro_2.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "place") val place: String,
    @ColumnInfo(name = "items") val items: Int,
    @ColumnInfo(name = "cost") val cost: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)