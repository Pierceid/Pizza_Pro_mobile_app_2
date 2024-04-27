package com.example.pizza_pro_2.domain.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedbackViewModel : ViewModel() {
    private val _state = MutableStateFlow(FeedbackState())
    val state = _state.asStateFlow()

    fun onEvent(event: FeedbackEvent) {
        viewModelScope.launch {
            when (event) {
                is FeedbackEvent.SatisfactionChanged -> {
                    _state.update { 
                        it.copy(satisfaction = event.satisfaction)
                    }
                }

                is FeedbackEvent.DeliveryTimeChanged -> {
                    _state.update {
                        it.copy(deliveryTime = event.deliveryTime)
                    }
                }

                is FeedbackEvent.ProductQualityChanged -> {
                    _state.update {
                        it.copy(productQuality = event.productQuality)
                    }
                }

                is FeedbackEvent.CustomerServiceChanged -> {
                    _state.update {
                        it.copy(customerService = event.customerService)
                    }
                }

                is FeedbackEvent.CommentChanged -> {
                    _state.update {
                        it.copy(comment = event.comment)
                    }
                }

                is FeedbackEvent.FollowUpChanged -> {
                    _state.update {
                        it.copy(followUp = event.followUp)
                    }
                }

                is FeedbackEvent.DialogVisibilityChanged -> {
                    _state.update {
                        it.copy(isDialogVisible = event.isVisible)
                    }
                }

                is FeedbackEvent.Discard -> {
                    _state.value = FeedbackState()
                }

                is FeedbackEvent.Send -> {
                    _state.value = FeedbackState()
                }
            }
        }
    }
}
