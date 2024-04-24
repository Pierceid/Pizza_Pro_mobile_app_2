package com.example.pizza_pro_2.navigation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.pizza_pro_2.domain.shared.SharedState
import com.example.pizza_pro_2.ui.theme.Lime
import com.example.pizza_pro_2.ui.theme.Orange
import com.example.pizza_pro_2.ui.theme.Pink
import com.example.pizza_pro_2.ui.theme.Red
import com.example.pizza_pro_2.ui.theme.Salmon
import com.example.pizza_pro_2.ui.theme.Yellow
import com.example.pizza_pro_2.util.Util.Companion.capitalizeText
import com.example.pizza_pro_2.util.Util.Companion.formatPrice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(sharedState: SharedState, onDismiss: (Boolean) -> Unit) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    val pizza = sharedState.selectedPizza!!

    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(),
        onDismissRequest = { onDismiss(false) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                shape = RectangleShape,
                contentPadding = PaddingValues(0.dp),
                onClick = { }
            ) {
                Image(
                    modifier = Modifier.aspectRatio(3f / 2f),
                    painter = painterResource(pizza.imageSource),
                    contentDescription = stringResource(R.string.pizza_image),
                    contentScale = ContentScale.FillBounds
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.star_24),
                        contentDescription = stringResource(R.string.star),
                        tint = Yellow
                    )

                    Text(
                        text = " ${pizza.rating.formatPrice()}",
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.time_24),
                        contentDescription = stringResource(R.string.time),
                        tint = Orange
                    )

                    Text(
                        text = " ${pizza.time} min",
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.flame_24),
                        contentDescription = stringResource(R.string.flame),
                        tint = Red
                    )

                    Text(
                        text = " ${pizza.calories} kcal",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${pizza.name!!.capitalizeText()} Pizza",
                style = MaterialTheme.typography.titleMedium,
                color = Pink
            )

            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    text = pizza.description!!,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 5,
                    overflow = TextOverflow.Ellipsis
                )

                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .background(if (isExpanded) Salmon else Lime, RoundedCornerShape(4.dp))
                            .padding(4.dp)
                            .align(Alignment.Center)
                            .clickable { isExpanded = !isExpanded },
                        text = stringResource(if (isExpanded) R.string.show_less else R.string.show_more)
                    )
                }
            }
        }
    }
}
