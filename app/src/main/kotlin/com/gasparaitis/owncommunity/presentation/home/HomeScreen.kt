package com.gasparaitis.owncommunity.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.domain.shared.story.model.Story
import com.gasparaitis.owncommunity.presentation.destinations.AlertListScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.HomeScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.PostScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.ProfileScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.StoryScreenDestination
import com.gasparaitis.owncommunity.presentation.main.bottomnavigation.BottomNavigationState
import com.gasparaitis.owncommunity.presentation.main.bottomnavigation.BottomNavigationViewModel
import com.gasparaitis.owncommunity.presentation.shared.composables.post.PostView
import com.gasparaitis.owncommunity.presentation.shared.composables.story.StoryProfileImage
import com.gasparaitis.owncommunity.presentation.utils.extensions.componentActivity
import com.gasparaitis.owncommunity.presentation.utils.modifier.noRippleClickable
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors
import com.gasparaitis.owncommunity.presentation.utils.theme.TextStyles
import com.gasparaitis.owncommunity.presentation.utils.theme.slightBottomDarkGradientBrush
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.collections.immutable.ImmutableList

@Destination(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel(),
    bottomNavigationViewModel: BottomNavigationViewModel =
        hiltViewModel(LocalContext.current.componentActivity),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val bottomNavigationState by bottomNavigationViewModel.uiState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()
    HomeContent(
        state = state,
        lazyListState = lazyListState,
        onEvent = viewModel::onEvent,
    )
    HomeEventHandler(
        event = state.event,
        onEventHandled = viewModel::onEventHandled,
        navigator = navigator,
    )
    BottomNavigationEventHandler(
        event = bottomNavigationState.event,
        onEventHandled = bottomNavigationViewModel::onEventHandled,
        lazyListState = lazyListState,
    )
}

@Composable
private fun HomeEventHandler(
    event: HomeState.Event?,
    onEventHandled: (HomeState.Event?) -> Unit,
    navigator: DestinationsNavigator,
) {
    LaunchedEffect(event) {
        when (event) {
            HomeState.NavigateToAlertListScreen -> {
                navigator.navigate(AlertListScreenDestination) {
                    popUpTo(HomeScreenDestination) { saveState = true }
                }
            }
            HomeState.NavigateToPostAuthorProfileScreen -> {
                navigator.navigate(ProfileScreenDestination) {
                    popUpTo(HomeScreenDestination) { saveState = true }
                }
            }
            HomeState.NavigateToPostScreen -> {
                navigator.navigate(PostScreenDestination) {
                    popUpTo(HomeScreenDestination) { saveState = true }
                }
            }
            HomeState.NavigateToStoryListScreen -> {
                navigator.navigate(StoryScreenDestination) {
                    popUpTo(HomeScreenDestination) { saveState = true }
                }
            }
            HomeState.OnAlertIconClick -> {}
            is HomeState.OnPostEvent -> {}
            is HomeState.OnStoryClick -> {}
            null -> {}
        }
        onEventHandled(event)
    }
}

@Composable
private fun BottomNavigationEventHandler(
    event: BottomNavigationState.Event?,
    onEventHandled: (BottomNavigationState.Event?) -> Unit,
    lazyListState: LazyListState,
) {
    LaunchedEffect(event) {
        if (event is BottomNavigationState.OnHomeIconRepeatClick &&
            lazyListState.canScrollBackward
        ) {
            lazyListState.animateScrollToItem(0)
            onEventHandled(event)
        }
    }
}

@Composable
private fun HomeContent(
    state: HomeState,
    lazyListState: LazyListState,
    onEvent: (HomeState.Event) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopRow(
            isAlertBadgeEnabled = !state.areAllAlertsRead,
            onAlertIconClick = { onEvent(HomeState.OnAlertIconClick) },
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
        ) {
            item {
                StoryPager(
                    items = state.stories,
                    onStoryClick = { onEvent(HomeState.OnStoryClick(it)) },
                )
            }
            items(
                count = state.posts.size,
                key = { state.posts[it].id },
            ) { index ->
                PostView(
                    item = state.posts[index],
                    onAction = { onEvent(HomeState.OnPostEvent(it)) },
                )
            }
        }
    }
}

@Composable
private fun TopRow(
    onAlertIconClick: () -> Unit,
    isAlertBadgeEnabled: Boolean
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp, bottom = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HomeTopRowTitle(
            title = stringResource(id = R.string.home_title),
        )
        HomeTopRowAlertIconButton(
            isBadgeEnabled = isAlertBadgeEnabled,
            onClick = onAlertIconClick,
        )
    }
}

@Composable
private fun HomeTopRowTitle(title: String) {
    Text(
        text = title,
        style = TextStyles.title,
    )
}

@Composable
private fun HomeTopRowAlertIconButton(
    onClick: () -> Unit,
    isBadgeEnabled: Boolean
) {
    Box(
        modifier = Modifier.noRippleClickable(onClick),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_message),
            tint = Colors.PureWhite,
            contentDescription = "Favorite",
        )
        if (!isBadgeEnabled) return@Box
        Icon(
            modifier =
                Modifier
                    .size(10.dp)
                    .zIndex(1f)
                    .align(Alignment.TopEnd),
            painter = painterResource(id = R.drawable.ic_alert_badge),
            tint = Color.Unspecified,
            contentDescription = "Alert badge",
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StoryPager(
    items: ImmutableList<Story>,
    onStoryClick: (Story) -> Unit,
    itemWidth: Dp = 100.dp,
    itemHeight: Dp = 140.dp,
) {
    val state = rememberPagerState { items.size }
    HorizontalPager(
        modifier =
            Modifier.padding(
                start = 24.dp,
                bottom = 24.dp,
            ),
        state = state,
        pageSpacing = 12.dp,
        pageSize = PageSize.Fixed(itemWidth),
        beyondBoundsPageCount = 5,
    ) { index ->
        if (items[index].entries.isEmpty()) return@HorizontalPager
        HomeStoryPagerItem(
            itemWidth = itemWidth,
            itemHeight = itemHeight,
            profileImage = painterResource(id = items[index].profile.profileImage),
            storyImage = painterResource(id = items[index].entries.first().drawableResId),
            isStoryRead = items[index].isRead,
            onClick = { onStoryClick(items[index]) },
        )
    }
}

@Composable
private fun HomeStoryPagerItem(
    itemWidth: Dp,
    itemHeight: Dp,
    profileImage: Painter,
    storyImage: Painter,
    isStoryRead: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .size(width = itemWidth, height = itemHeight)
                .noRippleClickable(onClick),
    ) {
        HomeStoryHorizontalPagerItemMainImage(
            modifier = Modifier.zIndex(0f),
            itemWidth = itemWidth,
            itemHeight = itemHeight,
            image = storyImage,
        )
        StoryProfileImage(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .zIndex(2f),
            onClick = onClick,
            image = profileImage,
            shouldShowBorder = !isStoryRead,
        )
        HomeStoryHorizontalPagerItemDarkGradientBox(
            modifier = Modifier.zIndex(1f),
            itemWidth = itemWidth,
            itemHeight = itemHeight,
        )
    }
}

@Composable
private fun HomeStoryHorizontalPagerItemMainImage(
    itemWidth: Dp,
    itemHeight: Dp,
    image: Painter,
    modifier: Modifier = Modifier,
) {
    Image(
        modifier =
            Modifier
                .size(width = itemWidth, height = itemHeight)
                .clip(RoundedCornerShape(16.dp))
                .then(modifier),
        contentScale = ContentScale.FillHeight,
        painter = image,
        contentDescription = "Story image",
    )
}

@Composable
private fun HomeStoryHorizontalPagerItemDarkGradientBox(
    itemWidth: Dp,
    itemHeight: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            Modifier
                .size(width = itemWidth, height = itemHeight)
                .clip(RoundedCornerShape(16.dp))
                .background(brush = slightBottomDarkGradientBrush())
                .then(modifier),
    )
}

@Preview(showSystemUi = true)
@Composable
private fun ScreenPreview() {
    HomeContent(
        state = HomeState.EMPTY,
        lazyListState = rememberLazyListState(),
        onEvent = {},
    )
}
