package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.database.entities.Order
import com.example.pizza_pro_2.options.OrderSortType
import com.example.pizza_pro_2.ui.theme.Lime
import com.example.pizza_pro_2.ui.theme.Red
import com.example.pizza_pro_2.ui.theme.White
import com.example.pizza_pro_2.util.Util.Companion.capitalizeText
import com.example.pizza_pro_2.util.Util.Companion.formatPrice
import com.example.pizza_pro_2.util.Util.Companion.formatTime

@Composable
fun HistoryOrderCard(order: Order, onClick: () -> Unit, orderSortType: OrderSortType) {
    Card(
        modifier = Modifier.drawBehind { RoundedCornerShape(8.dp) },
        border = BorderStroke(1.dp, White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${order.id}. ${order.name.capitalizeText()}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Red
                )

                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = onClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clear),
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }

            Text(
                text = "Time: ${order.time.formatTime()}",
                color = if (orderSortType == OrderSortType.TIME) Lime else MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = "Place: ${order.place}",
                color = if (orderSortType == OrderSortType.PLACE) Lime else MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = "Purchase: (${order.items}) ${order.cost.formatPrice()}",
                color = if (orderSortType == OrderSortType.PURCHASE) Lime else MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
