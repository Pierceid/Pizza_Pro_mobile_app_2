package com.example.pizza_pro_2.util

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

        fun String.formatDouble(form: String): String {
            return form.format(this.toDouble()).replace(',', '.')
        }

    }
}