package com.example.pizza_pro_2.database

import android.content.Context

class MyContainer(private  val context: Context) {

    val myRepository: MyRepository by lazy {
        MyRepository(MyDatabase.getInstance(context = context).myDao)
    }
}