package com.example.pizza_pro_2.use_cases

class ValidateLocation {
    fun execute(location: String): ValidationResult {
        if (location.isBlank()) {
            return ValidationResult(
                false,
                "Location must not be blank."
            )
        }
        return ValidationResult(true)
    }
}