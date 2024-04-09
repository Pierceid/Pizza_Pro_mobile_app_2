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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.pizza_pro_2.domain.feedback.FeedbackFormEvent
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
    var isDialogVisible by rememberSaveable { mutableStateOf(false) }
    var option by rememberSaveable { mutableIntStateOf(0) }

    val viewModel = viewModel<FeedbackViewModel>()
    val state = viewModel.state
    val context = LocalContext.current
    val dialogTitle =
        stringResource(id = if (option == 0) R.string.discard_feedback else R.string.share_feedback)
    val dialogText =
        stringResource(id = if (option == 0) R.string.are_you_sure_you_want_to_discard_your_feedback else R.string.would_you_like_to_proceed_and_provide_us_with_your_feedback)
    val toastMessage =
        stringResource(id = if (option == 0) R.string.feedback_discarded_successfully else R.string.feedback_sent_successfully)
    val event = if (option == 0) FeedbackFormEvent.Discard else FeedbackFormEvent.Send
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
        if (isDialogVisible) {
            InfoDialog(
                title = dialogTitle,
                text = dialogText,
                onDismiss = { isDialogVisible = it },
                dismissButton = R.string.no,
                onConfirm = {
                    viewModel.onEvent(event)
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
                text = stringResource(id = R.string.how_would_you_rate_your_experience),
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            RadioGroup(
                selected = state.satisfaction,
                onSelectionChange = {
                    viewModel.onEvent(FeedbackFormEvent.SatisfactionChanged(it))
                },
                options = options,
                modifier = Modifier.padding(horizontal = 8.dp),
                imagePainterIds = imagePainterIds
            )

            Spacer(modifier = Modifier)

            HeaderText(
                text = stringResource(id = R.string.would_you_tell_us_a_little_more_about_your_experience),
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.delivery_time),
                    style = MaterialTheme.typography.titleSmall,
                    color = White
                )

                Row {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(FeedbackFormEvent.DeliveryTimeChanged(true))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(id = R.string.delivery_time),
                            tint = if (state.deliveryTime) Lime else Silver
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.onEvent(FeedbackFormEvent.DeliveryTimeChanged(false))
                        }
                    ) {
                        Icon(
                            modifier = Modifier.rotate(180f),
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(id = R.string.delivery_time),
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
                    text = stringResource(id = R.string.product_quality),
                    style = MaterialTheme.typography.titleSmall,
                    color = White
                )

                Row {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(FeedbackFormEvent.ProductQualityChanged(true))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(id = R.string.product_quality),
                            tint = if (state.productQuality) Lime else Silver
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.onEvent(FeedbackFormEvent.ProductQualityChanged(false))
                        }
                    ) {
                        Icon(
                            modifier = Modifier.rotate(180f),
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(id = R.string.product_quality),
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
                    text = stringResource(id = R.string.customer_service),
                    style = MaterialTheme.typography.titleSmall,
                    color = White
                )

                Row {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(FeedbackFormEvent.CustomerServiceChanged(true))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(id = R.string.customer_service),
                            tint = if (state.customerService) Lime else Silver
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.onEvent(FeedbackFormEvent.CustomerServiceChanged(false))
                        }
                    ) {
                        Icon(
                            modifier = Modifier.rotate(180f),
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(id = R.string.customer_service),
                            tint = if (!state.customerService) Salmon else Silver
                        )
                    }
                }
            }

            Spacer(modifier = Modifier)

            HeaderText(
                text = stringResource(id = R.string.comment),
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            InputTextField(
                value = state.comment,
                onValueChange = {
                    viewModel.onEvent(FeedbackFormEvent.CommentChanged(it))
                },
                label = "",
                leadingIcon = Icons.Default.Create,
                trailingIcon = Icons.Default.Clear,
                onTrailingIconClick = {
                    viewModel.onEvent(FeedbackFormEvent.CommentChanged(""))
                },
                imeAction = ImeAction.Done
            )

            Spacer(modifier = Modifier.height(8.dp))

            HeaderText(
                text = stringResource(id = R.string.may_we_follow_you_up_on_your_feedback),
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.no),
                    style = MaterialTheme.typography.titleMedium,
                    color = White
                )

                Switch(
                    checked = state.followUp,
                    onCheckedChange = {
                        viewModel.onEvent(FeedbackFormEvent.FollowUpChanged(it))
                    },
                    thumbContent = {
                        Icon(
                            imageVector = if (state.followUp) Icons.Default.Check else Icons.Default.Clear,
                            tint = White,
                            contentDescription = stringResource(id = R.string.f_u_switch)
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
                    text = stringResource(id = R.string.yes),
                    style = MaterialTheme.typography.titleMedium,
                    color = White
                )
            }

            Spacer(modifier = Modifier)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ActionButton(
                    text = stringResource(id = R.string.discard),
                    onClick = {
                        option = 0
                        isDialogVisible = true
                    },
                    modifier = Modifier.weight(1f)
                )

                ActionButton(
                    text = stringResource(id = R.string.send),
                    onClick = {
                        option = 1
                        isDialogVisible = true
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}