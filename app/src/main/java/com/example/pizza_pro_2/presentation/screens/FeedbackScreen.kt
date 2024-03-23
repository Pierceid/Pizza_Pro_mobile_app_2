package com.example.pizza_pro_2.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pizza_pro_2.R
import com.example.pizza_pro_2.domain.FeedbackFormEvent
import com.example.pizza_pro_2.options.Impression
import com.example.pizza_pro_2.presentation.components.ActionButton
import com.example.pizza_pro_2.presentation.components.DefaultColumn
import com.example.pizza_pro_2.presentation.components.HeaderText
import com.example.pizza_pro_2.presentation.components.InputTextField
import com.example.pizza_pro_2.presentation.components.RadioGroup
import com.example.pizza_pro_2.ui.theme.Green
import com.example.pizza_pro_2.ui.theme.Orange
import com.example.pizza_pro_2.ui.theme.Red
import com.example.pizza_pro_2.ui.theme.Sea
import com.example.pizza_pro_2.ui.theme.Silver
import com.example.pizza_pro_2.ui.theme.Slate
import com.example.pizza_pro_2.ui.theme.Teal
import com.example.pizza_pro_2.ui.theme.White
import com.example.pizza_pro_2.ui.theme.Yellow
import com.example.pizza_pro_2.view_models.FeedbackViewModel

@Composable
fun FeedbackScreen(navController: NavController) {
    val viewModel = viewModel<FeedbackViewModel>()
    val state = viewModel.state

    val iconPainterId = when (state.impression) {
        Impression.GREAT -> R.drawable.great_160
        Impression.GOOD -> R.drawable.good_160
        Impression.DECENT -> R.drawable.decent_160
        Impression.BAD -> R.drawable.bad_160
    }

    val iconColor = when (state.impression) {
        Impression.GREAT -> Green
        Impression.GOOD -> Yellow
        Impression.DECENT -> Orange
        Impression.BAD -> Red
    }

    DefaultColumn {
        Column(
            modifier = Modifier.width(480.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderText(
                text = stringResource(id = R.string.what_are_your_impressions_of_the_application),
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            RadioGroup(
                inRow = false,
                selected = state.impression,
                onSelectionChange = {
                    viewModel.onEvent(FeedbackFormEvent.ImpressionChanged(it))
                },
                options = listOf(
                    Impression.GREAT,
                    Impression.GOOD,
                    Impression.DECENT,
                    Impression.BAD
                ),
                iconPainter = painterResource(iconPainterId),
                iconColor = iconColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            HeaderText(
                text = stringResource(id = R.string.do_you_have_any_thoughts_to_share),
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            InputTextField(
                value = state.thoughts,
                onValueChange = {
                    viewModel.onEvent(FeedbackFormEvent.ThoughtsChanged(it))
                },
                label = stringResource(id = R.string.thoughts),
                leadingIcon = Icons.Default.Create,
                trailingIcon = Icons.Default.Clear,
                onTrailingIconClick = {
                    viewModel.onEvent(FeedbackFormEvent.ThoughtsChanged(""))
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

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
                        checkedThumbColor = Sea,
                        uncheckedThumbColor = Slate,
                        checkedTrackColor = Teal,
                        uncheckedTrackColor = Silver
                    ),
                )

                Text(
                    text = stringResource(id = R.string.yes),
                    style = MaterialTheme.typography.titleMedium,
                    color = White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ActionButton(
                    text = stringResource(id = R.string.discard),
                    onClick = {
                        viewModel.onEvent(FeedbackFormEvent.Discard)
                    },
                    modifier = Modifier.weight(1f)
                )
                ActionButton(
                    text = stringResource(id = R.string.send),
                    onClick = {
                        viewModel.onEvent(FeedbackFormEvent.Send)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}