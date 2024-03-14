package com.example.pizza_pro_2.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.item.Pizza

private val DarkColorScheme = darkColorScheme(
    primary = Blue,
    onPrimary = White,
    primaryContainer = Blue,
    onPrimaryContainer = White,
    secondary = Black,
    onSecondary = White,
    secondaryContainer = Blue,
    onSecondaryContainer = White,
    tertiary = Black
)

private val LightColorScheme = lightColorScheme(
    primary = Sky,
    onPrimary = Black,
    primaryContainer = Sky,
    onPrimaryContainer = Black,
    secondary = White,
    onSecondary = Black,
    secondaryContainer = Sky,
    onSecondaryContainer = Black,
    tertiary = Black
)

@Composable
fun PizzaProCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(
                brush = Brush.horizontalGradient(colors = listOf(Iron, Grey)),
                shape = RoundedCornerShape(10.dp)
            )
            .border(border = BorderStroke(2.dp, Black), shape = RoundedCornerShape(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        content = content
    )
}

@Composable
fun PizzaProBackground(content: @Composable ColumnScope.() -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.blue_background),
            contentDescription = stringResource(id = R.string.background),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState(), true),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

@Composable
fun PizzaProButton(modifier: Modifier, text: String, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple,
            contentColor = White
        ),
        onClick = onClick
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun PizzaCard(pizza: Pizza) {
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(5.dp, 0.dp, 5.dp, 5.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.horizontalGradient(colors = listOf(Grey, Ashen)))
        ) {
            Image(
                modifier = Modifier
                    .padding(10.dp)
                    .border(border = BorderStroke(1.dp, Black))
                    .clickable(onClick = {}),
                painter = painterResource(id = pizza.imageSource),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 5.dp, 10.dp, 10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = pizza.name.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = Red,
                    modifier = Modifier.clickable(onClick = {})
                )
                Text(
                    text = String.format("%.2f €", pizza.cost),
                    style = MaterialTheme.typography.titleMedium,
                    color = Black
                )
                Box(
                    modifier = Modifier
                        .size(120.dp, 40.dp)
                        .background(
                            color = Orange,
                            shape = RoundedCornerShape(40.dp)
                        )
                        .border(
                            border = BorderStroke(1.dp, Black),
                            shape = RoundedCornerShape(40.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.plus),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.clickable(onClick = {})
                        )
                        Box(
                            modifier = Modifier
                                .size(40.dp, 40.dp)
                                .background(
                                    color = Sun,
                                    shape = RoundedCornerShape(40.dp)
                                )
                                .border(
                                    border = BorderStroke(1.dp, Black),
                                    shape = RoundedCornerShape(40.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "0",
                                style = MaterialTheme.typography.titleMedium,
                                color = Black
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.clickable(onClick = {})
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Pizza_Pro_2_Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.tertiary.toArgb()
            window.navigationBarColor = colorScheme.tertiary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}