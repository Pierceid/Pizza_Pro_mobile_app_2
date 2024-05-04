package com.example.pizza_pro_2.domain.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.shared.SharedEvent
import com.example.pizza_pro_2.ui.theme.Maroon
import com.example.pizza_pro_2.ui.theme.Mustard
import com.example.pizza_pro_2.ui.theme.Teal
import com.example.pizza_pro_2.use_cases.ValidatePlace
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(private val validatePlace: ValidatePlace = ValidatePlace()) : ViewModel() {
    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()

    fun onEvent(event: CartEvent) {
        viewModelScope.launch {
            when (event) {
                is CartEvent.CostsChanged -> {
                    val delivery = if (event.items == 0.0) 0.0 else 5.0
                    _state.update {
                        it.copy(
                            itemsCost = event.items,
                            deliveryCost = delivery,
                            totalCost = event.items + delivery
                        )
                    }
                }

                is CartEvent.PlaceChanged -> {
                    _state.update {
                        it.copy(orderPlace = event.place)
                    }
                }

                is CartEvent.PaymentTypeChanged -> {
                    _state.update {
                        it.copy(paymentType = event.type)
                    }
                }

                is CartEvent.OptionChanged -> {
                    _state.update {
                        it.copy(buttonOption = event.option, isDialogVisible = true)
                    }
                    when (event.option) {
                        0 -> {
                            _state.update {
                                it.copy(
                                    dialogTitleId = R.string.discard_order,
                                    dialogTextId = R.string.are_you_sure_you_want_to_discard_your_order,
                                    toastMessageId = R.string.order_discarded_successfully,
                                    dialogEvent = SharedEvent.DiscardOrder,
                                    dialogColor = Maroon,
                                    hasDialogInputField = false,
                                    hasRadioGroup = false
                                )
                            }
                        }

                        1 -> {
                            _state.update {
                                it.copy(
                                    dialogTitleId = R.string.choose_details,
                                    dialogTextId = R.string.specify_delivery_details_for_your_order,
                                    dialogColor = Mustard,
                                    hasDialogInputField = true,
                                    hasRadioGroup = true
                                )
                            }
                        }

                        2 -> {
                            _state.update {
                                it.copy(
                                    dialogTitleId = R.string.place_order,
                                    dialogTextId = R.string.would_you_like_to_proceed_and_place_your_order,
                                    toastMessageId = R.string.order_placed_successfully,
                                    dialogEvent = SharedEvent.PlaceOrder(
                                        _state.value.orderPlace,
                                        _state.value.totalCost,
                                        _state.value.paymentType
                                    ),
                                    dialogColor = Teal,
                                    hasDialogInputField = false,
                                    hasRadioGroup = false
                                )
                            }
                        }

                        else -> {}
                    }
                }

                is CartEvent.DialogVisibilityChanged -> {
                    _state.update {
                        it.copy(isDialogVisible = event.isVisible)
                    }
                }

                CartEvent.SubmitForm -> {
                    validateOrderPlace()
                    if (_state.value.isValidForm) {
                        onEvent(CartEvent.OptionChanged(2))
                    }
                }
            }
        }
    }

    private fun validateOrderPlace() {
        val placeResult = validatePlace.execute(_state.value.orderPlace)
        val hasError = listOf(placeResult).any { !it.successful }

        if (hasError) {
            _state.update {
                it.copy(
                    placeErrorId = placeResult.errorMessageId,
                    isValidForm = false
                )
            }
        } else {
            _state.update {
                it.copy(
                    placeErrorId = null,
                    isValidForm = true
                )
            }
        }
    }
}
