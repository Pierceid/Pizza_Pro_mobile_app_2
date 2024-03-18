package com.example.pizza_pro_2.use_case

import android.util.Patterns

class ValidateEmail {
    fun execute(email: String): ValidationResult {
        if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                false,
                "Email address must be unique and in valid form."
            )
        }
        return ValidationResult(true)
    }
}