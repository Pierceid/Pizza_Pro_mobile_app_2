package com.example.pizza_pro_2.util

import android.text.format.DateFormat
import java.text.NumberFormat
import java.util.Locale

class Util {

    companion object {

        fun String.capitalizeText(): String {
            return split(" ").joinToString(" ") { it ->
                it.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                }
            }
        }

        fun Double.formatPrice(): String {
            return NumberFormat.getCurrencyInstance().format(this).toString().replace(',', '.')
        }

        fun Long.formatTime(): String {
            return DateFormat.format("d.M.yyyy (h:mm a)", this).toString()
        }
    }
}
