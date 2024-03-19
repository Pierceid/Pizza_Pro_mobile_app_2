package com.example.pizza_pro_2.use_cases

class ValidateName {
    fun execute(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                false,
                "Name must be unique and not blank."
            )
        }
        return ValidationResult(true)
    }
}