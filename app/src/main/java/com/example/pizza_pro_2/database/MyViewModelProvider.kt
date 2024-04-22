package com.example.pizza_pro_2.database

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pizza_pro_2.MyApplication
import com.example.pizza_pro_2.domain.shared.SharedViewModel

object MyViewModelProvider {

    val factory = viewModelFactory {
        initializer {
            SharedViewModel(MyApplication().myContainer.myRepository)
        }
    }
}