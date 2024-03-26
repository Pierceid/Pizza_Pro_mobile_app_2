package com.example.pizza_pro_2.domain

import com.example.pizza_pro_2.options.Satisfaction

sealed class FeedbackFormEvent {
    data class SatisfactionChanged(val satisfaction: Satisfaction): FeedbackFormEvent()
    data class ThoughtsChanged(val thoughts: String): FeedbackFormEvent()
    data class FollowUpChanged(val followUp: Boolean): FeedbackFormEvent()
    data object Discard: FeedbackFormEvent()
    data object Send: FeedbackFormEvent()
}