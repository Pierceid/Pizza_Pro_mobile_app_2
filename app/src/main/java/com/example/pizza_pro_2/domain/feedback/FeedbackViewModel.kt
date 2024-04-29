package com.example.pizza_pro_2.domain.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.ui.theme.Maroon
import com.example.pizza_pro_2.ui.theme.Teal
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

                is FeedbackEvent.OptionChanged -> {
                    _state.update {
                        it.copy(buttonOption = event.option, isDialogVisible = true)
                    }
                    when (event.option) {
                        0 -> {
                            _state.update {
                                it.copy(
                                    dialogTitleId = R.string.discard_feedback,
                                    dialogTextId = R.string.are_you_sure_you_want_to_discard_your_feedback,
                                    toastMessageId = R.string.feedback_discarded_successfully,
                                    dialogEvent = FeedbackEvent.DiscardFeedback,
                                    dialogColor = Maroon
                                )
                            }
                        }

                        1 -> {
                            _state.update {
                                it.copy(
                                    dialogTitleId = R.string.share_feedback,
                                    dialogTextId = R.string.would_you_like_to_proceed_and_provide_us_with_your_feedback,
                                    toastMessageId = R.string.feedback_sent_successfully,
                                    dialogEvent = FeedbackEvent.SendFeedback,
                                    dialogColor = Teal
                                )
                            }
                        }

                        else -> {}
                    }
                }

                is FeedbackEvent.DiscardFeedback -> {
                    _state.value = FeedbackState()
                }

                is FeedbackEvent.SendFeedback -> {
                    _state.value = FeedbackState()
                }
            }
        }
    }
}
