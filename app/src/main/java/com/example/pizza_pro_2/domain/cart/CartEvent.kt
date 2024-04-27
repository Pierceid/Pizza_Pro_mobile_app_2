package com.example.pizza_pro_2.domain.cart

sealed class CartEvent {
    data class CostsChanged(val items: Double) : CartEvent()
    data class PlaceChanged(val place: String) : CartEvent()
    data class OptionChanged(val option: Int) : CartEvent()
    data class DialogVisibilityChanged(val isVisible: Boolean) : CartEvent()
}