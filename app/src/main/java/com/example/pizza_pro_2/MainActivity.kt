package com.example.pizza_pro_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.pizza_pro_2.navigation.nav_graph.NavGraph
import com.example.pizza_pro_2.ui.theme.Ashen
import com.example.pizza_pro_2.ui.theme.Black
import com.example.pizza_pro_2.ui.theme.Grey
import com.example.pizza_pro_2.ui.theme.Orange
import com.example.pizza_pro_2.ui.theme.Pizza_Pro_2_Theme
import com.example.pizza_pro_2.ui.theme.Red
import com.example.pizza_pro_2.ui.theme.Sun

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
