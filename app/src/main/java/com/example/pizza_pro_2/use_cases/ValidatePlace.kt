package com.example.pizza_pro_2.use_cases

import com.example.pizza_pro_2.R

class ValidatePlace {
    fun execute(place: String): ValidationResult {
        if (place.isBlank()) {
            return ValidationResult(
                false,
                R.string.place_must_not_be_blank
            )
        }
        return ValidationResult(true)
    }
}