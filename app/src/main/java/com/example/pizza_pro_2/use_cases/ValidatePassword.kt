package com.example.pizza_pro_2.use_cases

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if (password.length < 8 || !password.any { it.isDigit() } || !password.any { it.isLetter() }) {
            return ValidationResult(
                false,
                "Password must be at least 8 characters long and contain at least 1 letter and number."
            )
        }
        return ValidationResult(true)
    }
}