package com.example.pizza_pro_2.domain

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if (password.length < 8 || !password.any { it.isDigit() } || !password.any { it.isLetter() }) {
            return ValidationResult(
                false,
                "Password must be 8–100 characters long and contain at least 1 letter and number."
            )
        }
        return ValidationResult(true)
    }
}