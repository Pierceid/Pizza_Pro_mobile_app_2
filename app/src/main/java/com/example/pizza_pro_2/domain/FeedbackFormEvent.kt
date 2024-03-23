package com.example.pizza_pro_2.domain

import com.example.pizza_pro_2.options.Impression

sealed class FeedbackFormEvent {
    data class ImpressionChanged(val impression: Impression): FeedbackFormEvent()
    data class ThoughtsChanged(val thoughts: String): FeedbackFormEvent()
    data class FollowUpChanged(val followUp: Boolean): FeedbackFormEvent()
    data object Discard: FeedbackFormEvent()
    data object Send: FeedbackFormEvent()
}