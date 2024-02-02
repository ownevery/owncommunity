package com.gasparaitis.owncommunity.presentation.alertlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.domain.alerts.usecase.AlertListUseCase
import com.gasparaitis.owncommunity.presentation.destinations.PostScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.ProfileScreenDestination
import com.gasparaitis.owncommunity.presentation.utils.extensions.humanReadableTimeAgo
import com.gasparaitis.owncommunity.presentation.utils.modifier.noRippleClickable
import com.gasparaitis.owncommunity.presentation.utils.preview.ScreenPreview
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors
import com.gasparaitis.owncommunity.presentation.utils.theme.TextStyles
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AlertListScreen(
    navigator: DestinationsNavigator,
    viewModel: AlertListViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()
    AlertListScreenContent(
        state = state,
        lazyListState = lazyListState,
        onEvent = viewModel::onEvent,
    )
    AlertEventHandler(
        event = state.event,
        navigator = navigator,
        onEventHandled = viewModel::onEventHandled,
    )
}

@Composable
private fun AlertEventHandler(
    event: AlertListState.Event?,
    navigator: DestinationsNavigator,
    onEventHandled: (AlertListState.Event?) -> Unit,
) {
    LaunchedEffect(event) {
        when (event) {
            AlertListState.NavigateToPostScreen -> {
                navigator.navigate(PostScreenDestination)
            }

            AlertListState.NavigateToProfileScreen -> {
                navigator.navigate(ProfileScreenDestination)
            }

            is AlertListState.OnAlertItemClick -> {}
            AlertListState.OnMarkAllAsReadClick -> {}
            null -> {}
        }
        onEventHandled(event)
    }
}

@Composable
private fun AlertListScreenContent(
    state: AlertListState,
    lazyListState: LazyListState,
    onEvent: (AlertListState.Event) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopRow(
            onMarkAllAsReadClick = { onEvent(AlertListState.OnMarkAllAsReadClick) },
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
        ) {
            state.alertMap.forEach { (section, items) ->
                item {
                    SectionTitle(
                        title = stringResource(id = section.nameResId),
                    )
                    Spacer(Modifier.height(36.dp))
                }
                items(
                    count = items.size,
                ) { index ->
                    AlertListItem(
                        onClick = { onEvent(AlertListState.OnAlertItemClick(items[index])) },
                        isRead = items[index].isRead,
                        imagePainter = painterResource(items[index].type.icon),
                        title = items[index].title,
                        date = items[index].timestamp.humanReadableTimeAgo,
                    )
                }
            }
        }
    }
}

@Composable
private fun TopRow(onMarkAllAsReadClick: () -> Unit) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp, bottom = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.alerts_screen_title),
            style = TextStyles.title,
            lineHeight = 24.sp,
        )
        Text(
            modifier = Modifier.noRippleClickable(onClick = onMarkAllAsReadClick),
            text = stringResource(R.string.alerts_screen_mark_all_as_read),
            style =
                TextStyles.body.copy(
                    color = Colors.SocialPink,
                    fontWeight = FontWeight.Bold,
                ),
            lineHeight = 24.sp,
        )
    }
}

@Composable
private fun SectionTitle(title: String,) {
    Text(
        modifier = Modifier.padding(start = 24.dp),
        text = title.toUpperCase(Locale.current),
        style =
            TextStyles.body.copy(
                color = Colors.LightGray,
                letterSpacing = 1.3.sp,
            ),
    )
}

@Composable
private fun AlertListItem(
    onClick: () -> Unit,
    isRead: Boolean,
    imagePainter: Painter,
    title: String,
    date: String,
) {
    val modifier = if (isRead) Modifier.alpha(0.7f) else Modifier
    Column(
        modifier = Modifier.noRippleClickable(onClick = onClick),
    ) {
        Row(
            modifier =
                Modifier
                    .padding(horizontal = 24.dp)
                    .then(modifier),
        ) {
            AlertListItemIcon(
                imagePainter = imagePainter,
            )
            Column(
                modifier = Modifier.padding(start = 12.dp),
            ) {
                AlertListItemTitle(
                    title = title,
                )
                AlertListItemDate(
                    date = date,
                )
            }
        }
        AlertListItemDivider()
    }
}

@Composable
private fun AlertListItemIcon(imagePainter: Painter,) {
    Image(
        modifier =
            Modifier
                .size(40.dp),
        painter = imagePainter,
        contentDescription = "Alert icon",
    )
}

@Composable
private fun AlertListItemTitle(title: String) {
    Text(
        text = title,
        style =
            TextStyles.body.copy(
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp,
            ),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun AlertListItemDate(date: String) {
    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = date,
        style =
            TextStyles.secondary.copy(
                color = Colors.LightGray,
                lineHeight = 20.sp,
            ),
    )
}

@Composable
private fun AlertListItemDivider() {
    Divider(
        modifier =
            Modifier
                .padding(top = 16.dp, bottom = 24.dp)
                .fillMaxWidth(),
        thickness = 1.dp,
        color = Color.DarkGray,
    )
}

@ScreenPreview
@Composable
private fun ScreenPreview() {
    AlertListScreenContent(
        state = AlertListState.EMPTY.copy(alertMap = AlertListUseCase().getAlerts()),
        lazyListState = rememberLazyListState(),
        onEvent = {},
    )
}
