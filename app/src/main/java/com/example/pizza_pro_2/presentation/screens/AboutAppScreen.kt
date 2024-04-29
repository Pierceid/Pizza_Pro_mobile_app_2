package com.example.pizza_pro_2.presentation.screens

import androidx.compose.runtime.Composable
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.HeaderText

@Composable
fun AboutAppScreen() {
    DefaultColumn {
        HeaderText(textId = R.string.about_app)
    }
}
