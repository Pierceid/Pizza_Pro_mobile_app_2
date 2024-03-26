package com.example.pizza_pro_2.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.domain.FeedbackFormEvent
import com.example.pizza_pro_2.domain.FeedbackFormState
import kotlinx.coroutines.launch

class FeedbackViewModel : ViewModel() {
    var state: FeedbackFormState by mutableStateOf(FeedbackFormState())

    fun onEvent(event: FeedbackFormEvent) {
        viewModelScope.launch {
            when (event) {
                is FeedbackFormEvent.SatisfactionChanged -> {
                    state = state.copy(satisfaction = event.satisfaction)
                }

                is FeedbackFormEvent.ThoughtsChanged -> {
                    state = state.copy(thoughts = event.thoughts)
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
