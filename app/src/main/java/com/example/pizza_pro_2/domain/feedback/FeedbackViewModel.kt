package com.example.pizza_pro_2.domain.feedback

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FeedbackViewModel : ViewModel() {
    var state: FeedbackFormState by mutableStateOf(FeedbackFormState())

    fun onEvent(event: FeedbackFormEvent) {
        viewModelScope.launch {
            when (event) {
                is FeedbackFormEvent.SatisfactionChanged -> {
                    state = state.copy(satisfaction = event.satisfaction)
                }

                is FeedbackFormEvent.DeliveryTimeChanged -> {
                    state = state.copy(deliveryTime = event.deliveryTime)
                }

                is FeedbackFormEvent.ProductQualityChanged -> {
                    state = state.copy(productQuality = event.productQuality)
                }

                is FeedbackFormEvent.CustomerServiceChanged -> {
                    state = state.copy(customerService = event.customerService)
                }

                is FeedbackFormEvent.CommentChanged -> {
                    state = state.copy(comment = event.comment)
                }

                is FeedbackFormEvent.FollowUpChanged -> {
                    state = state.copy(followUp = event.followUp)
                }

                is FeedbackFormEvent.Discard -> {
                    state = FeedbackFormState()
                }

                is FeedbackFormEvent.Send -> {
                    state = FeedbackFormState()
                }
            }
        }
    }
}
