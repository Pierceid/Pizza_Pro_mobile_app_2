package com.example.pizza_pro_2.domain

sealed class ValidationEvent {
    data object Success : ValidationEvent()
}