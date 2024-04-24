package com.example.pizza_pro_2.use_cases

import com.example.pizza_pro_2.R

class ValidateLocation {
    fun execute(location: String): ValidationResult {
        if (location.isBlank()) {
            return ValidationResult(
                false,
                R.string.location_must_not_be_blank
            )
        }
        return ValidationResult(true)
    }
}