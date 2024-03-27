package com.example.pizza_pro_2.domain

import com.example.pizza_pro_2.options.Satisfaction

sealed class FeedbackFormEvent {
    data class SatisfactionChanged(val satisfaction: Satisfaction): FeedbackFormEvent()
    data class DeliveryTimeChanged(val deliveryTime: Boolean): FeedbackFormEvent()
    data class ProductQualityChanged(val productQuality: Boolean): FeedbackFormEvent()
    data class CustomerServiceChanged(val customerService: Boolean): FeedbackFormEvent()
    data class CommentChanged(val comment: String): FeedbackFormEvent()
    data class FollowUpChanged(val followUp: Boolean): FeedbackFormEvent()
    data object Discard: FeedbackFormEvent()
    data object Send: FeedbackFormEvent()
}