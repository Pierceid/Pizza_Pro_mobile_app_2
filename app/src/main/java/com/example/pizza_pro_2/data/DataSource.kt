package com.example.pizza_pro_2.data

import androidx.compose.runtime.Stable
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.models.Pizza

@Stable
class DataSource {

    @Stable
    fun loadData() : List<Pizza> {
        return listOf(
            Pizza("Neapolitan pizza", "Thin, soft crust with tomato sauce, fresh mozzarella, basil, and olive oil. A simple, traditional Italian delight.", R.drawable.neapolitan_pizza, 4.8, 20, 850, 10.50),
            Pizza("New York pizza", "Large, thin slices with a foldable crust, topped with classic tomato sauce and mozzarella. An iconic NYC favorite.", R.drawable.newyork_pizza, 4.5, 23, 800, 9.20),
            Pizza("California pizza", "Artisanal, thin-crust pizza with creative toppings like arugula, goat cheese, and sun-dried tomatoes. A West Coast culinary adventure.", R.drawable.california_pizza, 4.0, 15 , 770, 9.50),
            Pizza("Greek pizza", "Thick, doughy crust with robust toppings including tomato sauce, mozzarella, olives, peppers, onions, and feta. A Mediterranean-inspired delight.", R.drawable.greek_pizza, 3.8, 18, 540, 10.00),
            Pizza("Roman pizza", "Thin, rectangular slices with a crispy crust, topped with a variety of ingredients. A Roman twist on classic pizza.", R.drawable.roman_pizza, 3.4, 20, 690, 10.20),
            Pizza("Pepperoni pizza", "Classic pizza topped with spicy pepperoni slices and melted mozzarella cheese. A savory, timeless favorite.", R.drawable.pepperoni_pizza, 4.0, 15, 770, 9.00),
            Pizza("Veggie pizza", "Colorful medley of fresh vegetables like tomatoes, bell peppers, onions, and olives on a pizza. A healthy and flavorful choice.", R.drawable.veggie_pizza, 4.2, 10, 450, 8.20),
            Pizza("Chicken pizza", "Grilled chicken pieces atop a pizza with a variety of toppings, often including barbecue or Alfredo sauce. A hearty and satisfying option.", R.drawable.chicken_pizza, 4.6, 20, 690, 10.50),
            Pizza("Hawaiian pizza", "Sweet and savory combination of ham and pineapple on a pizza with tomato sauce and mozzarella. A tropical twist on classic pizza.", R.drawable.hawaiian_pizza, 3.5, 12, 580, 9.60),
            Pizza("Supreme pizza", "Loaded with a variety of toppings such as pepperoni, sausage, bell peppers, onions, olives, and mushrooms. An all-in-one pizza experience.", R.drawable.supreme_pizza, 4.8, 20, 850, 12.00)
        )
    }
}