package com.example.pizza_pro_2.use_cases

import android.util.Patterns

class ValidateEmail {
    fun execute(email: String, metCondition: Boolean, type: Int): ValidationResult {
        val message = when (type) {
            1 -> "No match found for such email."
            else -> "Email must be unique and in valid form."
        }

        if (email.isBlank() || !metCondition || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = message
            )
        }
        return ValidationResult(successful = true)
    }
}