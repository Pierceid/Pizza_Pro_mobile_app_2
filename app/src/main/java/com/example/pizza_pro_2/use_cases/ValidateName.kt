package com.example.pizza_pro_2.use_cases

import com.example.pizza_pro_2.R

class ValidateName {
    fun execute(name: String, metCondition: Boolean): ValidationResult {
        if (name.isBlank() || !metCondition) {
            return ValidationResult(
                successful = false,
                errorMessageId = R.string.name_must_be_unique_and_not_blank
            )
        }
        return ValidationResult(successful = true)
    }
}