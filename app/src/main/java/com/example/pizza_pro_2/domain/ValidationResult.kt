package com.example.pizza_pro_2.domain

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
