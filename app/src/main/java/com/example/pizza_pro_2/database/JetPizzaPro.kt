package com.example.pizza_pro_2.database

import android.app.Application

class JetPizzaPro: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}