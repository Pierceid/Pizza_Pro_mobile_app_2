package com.example.pizza_pro_2.domain.shared

import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.database.entities.User
import com.example.pizza_pro_2.models.Pizza
import com.example.pizza_pro_2.options.Gender

sealed class SharedEvent {
    data class CurrentUserChanged(val user: User?): SharedEvent()
    data class SearchQueryChanged(val query: String): SharedEvent()
    data class PizzaCountChanged(val pizza: Pizza): SharedEvent()
    data class PizzaSelectionChanged(val pizza: Pizza): SharedEvent()
    data class SignUp(val user: User): SharedEvent()
    data class SignIn(val name: String, val email: String): SharedEvent()
    data class UpdateAccount(val name: String, val email: String, val password: String, val gender: Gender): SharedEvent()
    data class DeleteAccount(val name: String, val email: String): SharedEvent()
    data class PlaceOrder(val order: Order): SharedEvent()
    data class CancelOrder(val order: Order): SharedEvent()
    data object DiscardOrder: SharedEvent()
}