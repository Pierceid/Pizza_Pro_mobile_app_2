package com.example.pizza_pro_2.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review (
    @ColumnInfo(name = "user") val user: Int,
    @ColumnInfo(name = "time") val time: Long,
    @ColumnInfo(name = "joy") val joy: Int,
    @ColumnInfo(name = "details") val details: String,
    @ColumnInfo(name = "comment") val comment: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)