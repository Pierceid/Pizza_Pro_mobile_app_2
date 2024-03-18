package com.example.pizza_pro_2.domain

class ValidateName {
    fun execute(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                false,
                "Name must be unique and 1–100 characters long."
            )
        }
        return ValidationResult(true)
    }
}