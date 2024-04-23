package com.example.pizza_pro_2.domain.feedback

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FeedbackViewModel : ViewModel() {
    var state: FeedbackState by mutableStateOf(FeedbackState())

    fun onEvent(event: FeedbackEvent) {
        viewModelScope.launch {
            when (event) {
                is FeedbackEvent.SatisfactionChanged -> {
                    state = state.copy(satisfaction = event.satisfaction)
                }

                is FeedbackEvent.DeliveryTimeChanged -> {
                    state = state.copy(deliveryTime = event.deliveryTime)
                }

                is FeedbackEvent.ProductQualityChanged -> {
                    state = state.copy(productQuality = event.productQuality)
                }

                is FeedbackEvent.CustomerServiceChanged -> {
                    state = state.copy(customerService = event.customerService)
                }

                is FeedbackEvent.CommentChanged -> {
                    state = state.copy(comment = event.comment)
                }

                is FeedbackEvent.FollowUpChanged -> {
                    state = state.copy(followUp = event.followUp)
                }

                is FeedbackEvent.Discard -> {
                    state = FeedbackState()
                }

                is FeedbackEvent.Send -> {
                    state = FeedbackState()
                }
            }
        }
    }
}
