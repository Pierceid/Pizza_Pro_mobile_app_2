package com.example.pizza_pro_2.use_cases

import androidx.annotation.StringRes

data class ValidationResult(
    val successful: Boolean,
    @StringRes val errorMessageId: Int? = null
)
