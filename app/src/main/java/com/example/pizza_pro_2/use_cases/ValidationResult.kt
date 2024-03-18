package com.example.pizza_pro_2.use_cases

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
