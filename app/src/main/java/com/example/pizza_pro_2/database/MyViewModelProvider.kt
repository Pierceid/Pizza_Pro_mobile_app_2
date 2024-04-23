package com.example.pizza_pro_2.database

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pizza_pro_2.MyApplication
import com.example.pizza_pro_2.domain.auth.AuthViewModel
import com.example.pizza_pro_2.domain.shared.SharedViewModel

object MyViewModelProvider {

    val factory = viewModelFactory {
        initializer {
            AuthViewModel(myRepository = myApplication().myContainer.myRepository)
        }
        initializer {
            SharedViewModel(myRepository = myApplication().myContainer.myRepository)
        }
    }

    private fun CreationExtras.myApplication(): MyApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
}