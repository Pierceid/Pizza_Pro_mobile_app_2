package com.example.pizza_pro_2.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @ColumnInfo(name = "user") val user: Int,
    @ColumnInfo(name = "time") val time: Long,
    @ColumnInfo(name = "place") val place: String,
    @ColumnInfo(name = "items") val items: Int,
    @ColumnInfo(name = "cost") val cost: Double,
    @ColumnInfo(name = "payment") val payment: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)