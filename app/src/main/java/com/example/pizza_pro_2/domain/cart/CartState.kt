package com.example.pizza_pro_2.domain.cart

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.shared.SharedEvent
import com.example.pizza_pro_2.options.Payment
import com.example.pizza_pro_2.ui.theme.Slate

@Stable
data class CartState(
    val itemsCount: Int = 0,
    val itemsCost: Double = 0.0,
    val deliveryCost: Double = 0.0,
    val totalCost: Double = 0.0,
    val orderPlace: String = "",
    val paymentType: Payment = Payment.CASH,
    val isDialogVisible: Boolean = false,
    val buttonOption: Int = -1,
    val dialogTitleId: Int = R.string.empty,
    val dialogTextId: Int = R.string.empty,
    val toastMessageId: Int = R.string.empty,
    val dialogEvent: SharedEvent? = null,
    val dialogColor: Color = Slate,
    val hasDialogInputField: Boolean = false,
    val hasRadioGroup: Boolean = false,
    val isValidForm: Boolean = true,
    @StringRes val placeErrorId: Int? = null
)