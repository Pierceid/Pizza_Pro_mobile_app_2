@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pizza_pro_2.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.ui.theme.Lime
import com.example.pizza_pro_2.ui.theme.Red
import com.example.pizza_pro_2.ui.theme.Salmon
import com.example.pizza_pro_2.util.Util.Companion.capitalizeText
import com.example.pizza_pro_2.util.Util.Companion.formatDouble
import com.example.pizza_pro_2.view_models.SharedViewModel

@Composable
fun BottomSheet(sharedViewModel: SharedViewModel, onDismiss: (Boolean) -> Unit) {
    val pizza = sharedViewModel.state.selectedPizza!!
    var expanded by rememberSaveable { mutableStateOf(false) }

    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(),
        onDismissRequest = { onDismiss(false) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                shape = RectangleShape,
                contentPadding = PaddingValues(0.dp),
                onClick = { }
            ) {
                Image(
                    modifier = Modifier.aspectRatio(4f / 3f),
                    painter = painterResource(id = pizza.imageSource),
                    contentDescription = stringResource(id = R.string.pizza_image),
                    contentScale = ContentScale.FillBounds
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = stringResource(id = R.string.star)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Rating: ${pizza.rating.toString().formatDouble()}")
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(id = R.string.time)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Time: ${pizza.time} min")
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(id = R.string.flame)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Calories: ${pizza.calories} kcal")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${pizza.name!!.capitalizeText()} Pizza",
                style = MaterialTheme.typography.titleMedium,
                color = Red
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = pizza.description!!,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = if (expanded) Int.MAX_VALUE else 5,
                    overflow = TextOverflow.Ellipsis
                )

                Box(Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier
                            .background(
                                color = if (expanded) Salmon else Lime,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(4.dp)
                            .align(Alignment.Center)
                            .clickable { expanded = !expanded },
                        text = stringResource(id = if (expanded) R.string.show_less else R.string.show_more)
                    )
                }
            }
        }
    }
}
