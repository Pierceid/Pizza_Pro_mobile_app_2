package com.example.pizza_pro_2.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.database.entities.Review
import com.example.pizza_pro_2.options.Satisfaction
import com.example.pizza_pro_2.ui.theme.Lime
import com.example.pizza_pro_2.ui.theme.Red
import com.example.pizza_pro_2.ui.theme.Salmon
import com.example.pizza_pro_2.ui.theme.White
import com.example.pizza_pro_2.util.Util.Companion.formatTime

@Composable
fun HistoryReviewCard(review: Review, onClick: () -> Unit) {
    val joyImage = when (review.joy) {
        0 -> R.drawable.amazing
        1 -> R.drawable.great
        2 -> R.drawable.good
        3 -> R.drawable.bad
        4 -> R.drawable.awful
        else -> R.drawable.good
    }
    val joyText = when (review.joy) {
        0 -> Satisfaction.AMAZING
        1 -> Satisfaction.GREAT
        2 -> Satisfaction.GOOD
        3 -> Satisfaction.BAD
        4 -> Satisfaction.AWFUL
        else -> Satisfaction.GOOD
    }
    val detailsNumbers = Regex("\\d+").findAll(review.details).map { it.value.toInt() }.toList()

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
                    text = "ID: ${review.id}",
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
                text = "Time: ${review.time.formatTime()}",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Joy: $joyText ",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(joyImage),
                    contentDescription = stringResource(R.string.option_image),
                    contentScale = ContentScale.FillBounds
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Details: ",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                detailsNumbers.forEachIndexed { index, value ->
                    Text(
                        text = when (index) {
                            0 -> "Delivery "
                            1 -> "Quality "
                            else -> "Service "
                        },
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Icon(
                        modifier = Modifier
                            .size(16.dp)
                            .rotate(if (value == 0) 180f else 0f),
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = stringResource(R.string.product_quality),
                        tint = if (value == 0) Salmon else Lime
                    )

                    if (index != detailsNumbers.lastIndex) {
                        Text(
                            text = ",",
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }

            Text(
                text = "Comment: ${review.comment}",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
