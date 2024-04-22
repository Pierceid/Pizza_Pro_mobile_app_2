package com.example.pizza_pro_2.use_cases

class ValidatePassword {
    fun execute(password: String, metCondition: Boolean, isSignUp: Boolean): ValidationResult {
        val message =
            if (isSignUp) "Password must be at least 8 characters long and contain at least 1 letter and number."
            else "Incorrect password."

        if (password.length < 8 || !metCondition) {
            return ValidationResult(
                successful = false,
                errorMessage = message
            )
        }
        return ValidationResult(successful = true)
    }
}