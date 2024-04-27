package com.example.pizza_pro_2.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.feedback.FeedbackEvent
import com.example.pizza_pro_2.domain.feedback.FeedbackViewModel
import com.example.pizza_pro_2.options.Satisfaction
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.HeaderText
import com.example.pizza_pro_2.presentation.components.InfoDialog
import com.example.pizza_pro_2.presentation.components.InputTextField
import com.example.pizza_pro_2.presentation.components.RadioGroup
import com.example.pizza_pro_2.ui.theme.Lime
import com.example.pizza_pro_2.ui.theme.Maroon
import com.example.pizza_pro_2.ui.theme.Salmon
import com.example.pizza_pro_2.ui.theme.Sea
import com.example.pizza_pro_2.ui.theme.Silver
import com.example.pizza_pro_2.ui.theme.Slate
import com.example.pizza_pro_2.ui.theme.Teal
import com.example.pizza_pro_2.ui.theme.White

@Composable
fun FeedbackScreen(navController: NavController) {
    var option by rememberSaveable { mutableIntStateOf(0) }

    val viewModel: FeedbackViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val dialogTitleId = if (option == 0) R.string.discard_feedback else R.string.share_feedback
    val dialogTextId =
        if (option == 0) R.string.are_you_sure_you_want_to_discard_your_feedback
        else R.string.would_you_like_to_proceed_and_provide_us_with_your_feedback
    val toastMessage = stringResource(
        if (option == 0) R.string.feedback_discarded_successfully
        else R.string.feedback_sent_successfully
    )
    val event = if (option == 0) FeedbackEvent.Discard else FeedbackEvent.Send
    val color = if (option == 0) Maroon else Teal
    val options = listOf(
        Satisfaction.AWFUL,
        Satisfaction.BAD,
        Satisfaction.GOOD,
        Satisfaction.GREAT,
        Satisfaction.AMAZING
    )
    val imagePainterIds = listOf(
        R.drawable.awful,
        R.drawable.bad,
        R.drawable.good,
        R.drawable.great,
        R.drawable.amazing
    )

    DefaultColumn {
        if (state.isDialogVisible) {
            InfoDialog(
                titleId = dialogTitleId,
                textId = dialogTextId,
                onDismiss = {
                    viewModel.onEvent(FeedbackEvent.DialogVisibilityChanged(false))
                },
                dismissButton = R.string.no,
                onConfirm = {
                    viewModel.onEvent(event)
                    viewModel.onEvent(FeedbackEvent.DialogVisibilityChanged(false))
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
                },
                confirmButton = R.string.yes,
                color = color
            )
        }

        Column(
            modifier = Modifier.width(480.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderText(
                textId = R.string.how_would_you_rate_your_experience,
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            RadioGroup(
                selected = state.satisfaction,
                onSelectionChange = {
                    viewModel.onEvent(FeedbackEvent.SatisfactionChanged(it))
                },
                options = options,
                modifier = Modifier.padding(horizontal = 8.dp),
                imagePainterIds = imagePainterIds
            )

            Spacer(Modifier)

            HeaderText(
                textId = R.string.would_you_tell_us_a_little_more_about_your_experience,
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.delivery_time),
                    style = MaterialTheme.typography.titleSmall,
                    color = White
                )

                Row {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(FeedbackEvent.DeliveryTimeChanged(true))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(R.string.delivery_time),
                            tint = if (state.deliveryTime) Lime else Silver
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.onEvent(FeedbackEvent.DeliveryTimeChanged(false))
                        }
                    ) {
                        Icon(
                            modifier = Modifier.rotate(180f),
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(R.string.delivery_time),
                            tint = if (!state.deliveryTime) Salmon else Silver
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.product_quality),
                    style = MaterialTheme.typography.titleSmall,
                    color = White
                )

                Row {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(FeedbackEvent.ProductQualityChanged(true))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(R.string.product_quality),
                            tint = if (state.productQuality) Lime else Silver
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.onEvent(FeedbackEvent.ProductQualityChanged(false))
                        }
                    ) {
                        Icon(
                            modifier = Modifier.rotate(180f),
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(R.string.product_quality),
                            tint = if (!state.productQuality) Salmon else Silver
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.customer_service),
                    style = MaterialTheme.typography.titleSmall,
                    color = White
                )

                Row {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(FeedbackEvent.CustomerServiceChanged(true))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(R.string.customer_service),
                            tint = if (state.customerService) Lime else Silver
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.onEvent(FeedbackEvent.CustomerServiceChanged(false))
                        }
                    ) {
                        Icon(
                            modifier = Modifier.rotate(180f),
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(R.string.customer_service),
                            tint = if (!state.customerService) Salmon else Silver
                        )
                    }
                }
            }

            Spacer(Modifier)

            HeaderText(
                textId = R.string.comment,
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            InputTextField(
                value = state.comment,
                onValueChange = {
                    viewModel.onEvent(FeedbackEvent.CommentChanged(it))
                },
                labelId = R.string.empty,
                leadingIcon = Icons.Default.Create,
                trailingIcon = Icons.Default.Clear,
                onTrailingIconClick = {
                    viewModel.onEvent(FeedbackEvent.CommentChanged(""))
                },
                imeAction = ImeAction.Done
            )

            Spacer(Modifier.height(8.dp))

            HeaderText(
                textId = R.string.may_we_follow_you_up_on_your_feedback,
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.no),
                    style = MaterialTheme.typography.titleMedium,
                    color = White
                )

                Switch(
                    checked = state.followUp,
                    onCheckedChange = {
                        viewModel.onEvent(FeedbackEvent.FollowUpChanged(it))
                    },
                    thumbContent = {
                        Icon(
                            imageVector = if (state.followUp) Icons.Default.Check else Icons.Default.Clear,
                            tint = White,
                            contentDescription = stringResource(R.string.switcher)
                        )
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Teal,
                        uncheckedThumbColor = Slate,
                        checkedTrackColor = Sea,
                        uncheckedTrackColor = Silver
                    ),
                )

                Text(
                    text = stringResource(R.string.yes),
                    style = MaterialTheme.typography.titleMedium,
                    color = White
                )
            }

            Spacer(Modifier)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ActionButton(
                    textId = R.string.discard,
                    onClick = {
                        option = 0
                        viewModel.onEvent(FeedbackEvent.DialogVisibilityChanged(true))
                    },
                    modifier = Modifier.weight(1f)
                )

                ActionButton(
                    textId = R.string.send,
                    onClick = {
                        option = 1
                        viewModel.onEvent(FeedbackEvent.DialogVisibilityChanged(true))
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
