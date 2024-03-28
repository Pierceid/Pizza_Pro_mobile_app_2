package com.example.pizza_pro_2.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pizza_pro_2.options.Gender

@Entity(tableName = "users")
data class User(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "gender") val gender: Gender,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)