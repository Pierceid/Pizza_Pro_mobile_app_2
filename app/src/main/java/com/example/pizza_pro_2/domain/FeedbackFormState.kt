package com.example.pizza_pro_2.domain

import androidx.compose.runtime.Stable
import com.example.pizza_pro_2.options.Impression

@Stable
data class FeedbackFormState(
    val impression: Impression = Impression.GREAT,
    val thoughts: String = "",
    val followUp: Boolean = false
)