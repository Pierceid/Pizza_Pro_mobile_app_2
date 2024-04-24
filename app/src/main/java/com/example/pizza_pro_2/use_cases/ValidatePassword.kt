package com.example.pizza_pro_2.use_cases

class ValidatePassword {
    fun execute(password: String, metCondition: Boolean, type: Int): ValidationResult {
        val message = when (type) {
            1 -> "Incorrect password."
            else -> "Password must be at least 8 characters long and contain at least 1 letter and number."
        }

        if (password.length < 8 || !metCondition) {
            return ValidationResult(
                successful = false,
                errorMessage = message
            )
        }
        return ValidationResult(successful = true)
    }
}