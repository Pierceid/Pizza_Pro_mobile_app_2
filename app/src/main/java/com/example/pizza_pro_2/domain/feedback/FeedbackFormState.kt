package com.example.pizza_pro_2.domain.feedback

import androidx.compose.runtime.Stable
import com.example.pizza_pro_2.options.Satisfaction

@Stable
data class FeedbackFormState(
    val satisfaction: Satisfaction = Satisfaction.GOOD,
    val deliveryTime: Boolean = true,
    val productQuality: Boolean = true,
    val customerService: Boolean = true,
    val comment: String = "",
    val followUp: Boolean = false
)