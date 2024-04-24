package com.example.pizza_pro_2.use_cases

import com.example.pizza_pro_2.R

class ValidatePassword {
    fun execute(password: String, metCondition: Boolean, type: Int): ValidationResult {
        val message = when (type) {
            0, 2 -> R.string.password_must_be_at_least_8_characters_long_and_contain_at_least_1_letter_and_number
            1 -> R.string.incorrect_password
            else -> R.string.empty
        }

        if (password.length < 8 || !metCondition) {
            return ValidationResult(
                successful = false,
                errorMessageId = message
            )
        }
        return ValidationResult(successful = true)
    }
}