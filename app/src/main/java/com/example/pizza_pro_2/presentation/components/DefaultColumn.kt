package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.ui.theme.Azure
import com.example.pizza_pro_2.ui.theme.Blue
import com.example.pizza_pro_2.ui.theme.Sky

@Stable
@Composable
fun DefaultColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable ColumnScope.() -> Unit
) {
    val colors = if (isSystemInDarkTheme()) listOf(Azure, Blue) else listOf(Sky, Azure)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.radialGradient(colors = colors, radius = 1250f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .width(600.dp)
                .padding(8.dp)
                .verticalScroll(state = rememberScrollState(), enabled = true),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = verticalArrangement,
            content = content
        )
    }
}
