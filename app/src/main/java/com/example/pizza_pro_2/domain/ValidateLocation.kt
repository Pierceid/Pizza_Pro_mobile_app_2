package com.example.pizza_pro_2.domain

class ValidateLocation {
    fun execute(location: String): ValidationResult {
        if (location.isBlank()) {
            return ValidationResult(
                false,
                "Location must be 1–100 characters long."
            )
        }
        return ValidationResult(true)
    }
}