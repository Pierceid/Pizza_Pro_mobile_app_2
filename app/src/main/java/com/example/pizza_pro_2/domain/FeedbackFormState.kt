package com.example.pizza_pro_2.domain

import androidx.compose.runtime.Stable
import com.example.pizza_pro_2.options.Satisfaction

@Stable
data class FeedbackFormState(
    val satisfaction: Satisfaction = Satisfaction.GOOD,
    val thoughts: String = "",
    val followUp: Boolean = false
)