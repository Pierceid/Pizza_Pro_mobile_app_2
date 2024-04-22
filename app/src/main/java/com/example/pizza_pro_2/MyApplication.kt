package com.example.pizza_pro_2

import android.app.Application
import com.example.pizza_pro_2.database.MyContainer

class MyApplication: Application() {

    lateinit var myContainer: MyContainer

    override fun onCreate() {
        super.onCreate()
        myContainer = MyContainer(this)
    }
}