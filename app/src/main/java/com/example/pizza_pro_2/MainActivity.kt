package com.example.pizza_pro_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.pizza_pro_2.navigation.graphs.NavGraph
import com.example.pizza_pro_2.ui.theme.Pizza_Pro_2_Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pizza_Pro_2_Theme {
                NavGraph(navController = rememberNavController())
            }
        }
    }
}
