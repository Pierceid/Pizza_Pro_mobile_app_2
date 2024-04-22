package com.example.pizza_pro_2.use_cases

class ValidateName {
    fun execute(name: String, metCondition: Boolean): ValidationResult {
        if (name.isBlank() || !metCondition) {
            return ValidationResult(
                successful = false,
                errorMessage = "Name must be unique and not blank."
            )
        }
        return ValidationResult(successful = true)
    }
}