package com.example.pizza_pro_2.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pizza_pro_2.item.Pizza

class SharedViewModel : ViewModel() {
    var pizza by mutableStateOf<Pizza?>(null)
        private set

    fun addPizza(newPizza: Pizza) {
        pizza = newPizza
    }
}