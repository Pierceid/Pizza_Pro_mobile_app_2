package com.example.pizza_pro_2.database.entity

import androidx.room.*

@Entity(tableName = "order_table")
data class Order(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "time") var time: String = "",
    @ColumnInfo(name = "place") var place: String = "",
    @ColumnInfo(name = "items") var items: Int = 0,
    @ColumnInfo(name = "cost") var cost: String = ""
)