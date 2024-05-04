package com.example.pizza_pro_2.domain.feedback

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.options.Satisfaction
import com.example.pizza_pro_2.ui.theme.Slate

@Stable
data class FeedbackState(
    val satisfaction: Satisfaction = Satisfaction.GOOD,
    val deliveryTime: Boolean = true,
    val productQuality: Boolean = true,
    val customerService: Boolean = true,
    val comment: String = "",
    val followUp: Boolean = false,
    val isDialogVisible: Boolean = false,
    val buttonOption: Int = R.string.empty,
    val dialogTitleId: Int = R.string.empty,
    val dialogTextId: Int = R.string.empty,
    val toastMessageId: Int = R.string.empty,
    val dialogEvent: FeedbackEvent? = null,
    val dialogColor: Color = Slate
)