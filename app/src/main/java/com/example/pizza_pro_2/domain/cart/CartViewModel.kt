package com.example.pizza_pro_2.domain.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.shared.SharedEvent
import com.example.pizza_pro_2.ui.theme.Maroon
import com.example.pizza_pro_2.ui.theme.Teal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
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

                is CartEvent.OptionChanged -> {
                    _state.update {
                        it.copy(buttonOption = event.option)
                    }
                    when (event.option) {
                        0 -> {
                            _state.update {
                                it.copy(
                                    dialogTitleId = R.string.discard_order,
                                    dialogTextId = R.string.are_you_sure_you_want_to_discard_your_order,
                                    toastMessageId = R.string.order_discarded_successfully,
                                    dialogEvent = SharedEvent.DiscardOrder,
                                    dialogColor = Maroon
                                )
                            }
                        }

                        1 -> {
                            _state.update {
                                it.copy(
                                    dialogTitleId = R.string.place_order,
                                    dialogTextId = R.string.would_you_like_to_proceed_and_place_your_order,
                                    toastMessageId = R.string.order_placed_successfully,
                                    dialogEvent = SharedEvent.PlaceOrder(_state.value.orderPlace, _state.value.totalCost),
                                    dialogColor = Teal
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
            }
        }
    }
}
