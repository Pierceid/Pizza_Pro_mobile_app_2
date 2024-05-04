package com.example.pizza_pro_2.domain.cart

import com.example.pizza_pro_2.options.Payment

sealed class CartEvent {
    data class CostsChanged(val items: Double) : CartEvent()
    data class PlaceChanged(val place: String) : CartEvent()
    data class PaymentTypeChanged(val type: Payment) : CartEvent()
    data class OptionChanged(val option: Int) : CartEvent()
    data class DialogVisibilityChanged(val isVisible: Boolean) : CartEvent()
    data object SubmitForm : CartEvent()
}