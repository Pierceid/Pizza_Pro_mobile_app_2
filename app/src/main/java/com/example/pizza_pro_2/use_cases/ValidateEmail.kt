package com.example.pizza_pro_2.use_cases

import android.util.Patterns
import com.example.pizza_pro_2.R

class ValidateEmail {
    fun execute(email: String, metCondition: Boolean, type: Int): ValidationResult {
        val message = when (type) {
            0, 2 -> R.string.email_must_be_unique_and_in_valid_form
            1 -> R.string.no_match_found_for_such_email
            else -> R.string.empty
        }

        if (email.isBlank() || !metCondition || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessageId = message
            )
        }
        return ValidationResult(successful = true)
    }
}