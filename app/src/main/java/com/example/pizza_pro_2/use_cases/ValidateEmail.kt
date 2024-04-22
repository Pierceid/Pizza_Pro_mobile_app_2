package com.example.pizza_pro_2.use_cases

import android.util.Patterns

class ValidateEmail {
    fun execute(email: String, metCondition: Boolean, isSignUp: Boolean): ValidationResult {
        val message =
            if (isSignUp) "Email must be unique and in valid form."
            else "No match found for such email."

        if (email.isBlank() || !metCondition || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = message
            )
        }
        return ValidationResult(successful = true)
    }
}