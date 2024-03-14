package com.example.pizza_pro_2.database.entity

import androidx.room.*
import com.example.pizza_pro_2.option.Gender

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "email") var email: String = "",
    @ColumnInfo(name = "password") var password: String = "",
    @ColumnInfo(name = "location") var location: String = "",
    @ColumnInfo(name = "gender") var gender: Gender = Gender.OTHER
)