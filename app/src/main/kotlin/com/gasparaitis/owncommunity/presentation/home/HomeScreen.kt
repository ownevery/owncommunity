package com.gasparaitis.owncommunity.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.domain.shared.story.model.Story
import com.gasparaitis.owncommunity.presentation.destinations.HomeScreenDestination
import com.gasparaitis.owncommunity.presentation.shared.composables.post.PostView
import com.gasparaitis.owncommunity.presentation.utils.extensions.componentActivity
import com.gasparaitis.owncommunity.presentation.utils.modifier.noRippleClickable
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors
import com.gasparaitis.owncommunity.presentation.utils.theme.TextStyles
import com.gasparaitis.owncommunity.presentation.utils.theme.defaultGradientBrush
import com.gasparaitis.owncommunity.presentation.utils.theme.slightDarkGradientBrush
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@Destination(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel(LocalContext.current.componentActivity),
) {
    val state = viewModel.state.collectAsState()
    val lazyListState = rememberLazyListState()
    HomeContent(
        state = state.value,
        lazyListState = lazyListState,
        onAction = viewModel::onAction,
    )
    LaunchedEffect(Unit) {
        viewModel.navEvent.collect {
            if (it is HomeNavEvent.ScrollUp && lazyListState.canScrollBackward) {
                lazyListState.animateScrollToItem(0)
                return@collect
            }
            navigator.navigate(it.destination) {
                popUpTo(HomeScreenDestination) { saveState = true }
            }
        }
    }
}

@Composable
private fun HomeContent(
    state: HomeState,
    lazyListState: LazyListState,
    onAction: (HomeAction) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopRow(
            isAlertBadgeEnabled = !state.areAllAlertsRead,
            onAlertIconClick = { onAction(HomeAction.OnAlertIconClick) }
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
        ) {
            item {
                StoryPager(
                    items = state.stories,
                    onStoryClick = { onAction(HomeAction.OnStoryClick(it)) },
                )
            }
            items(
                count = state.posts.size,
                key = { state.posts[it].id },
            ) { index ->
                PostView(
                    item = state.posts[index],
                    onAction = { onAction(HomeAction.OnPostAction(it)) },
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
        modifier = Modifier
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
private fun HomeTopRowTitle(
    title: String,
) {
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
            modifier = Modifier
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
    items: List<Story>,
    onStoryClick: (Story) -> Unit,
    itemWidth: Dp = 100.dp,
    itemHeight: Dp = 140.dp,
) {
    val state = rememberPagerState()
    HorizontalPager(
        modifier = Modifier.padding(
            start = 24.dp,
            bottom = 24.dp,
        ),
        pageCount = items.size,
        pageSize = PageSize.Fixed(itemWidth),
        pageSpacing = 12.dp,
        state = state,
        beyondBoundsPageCount = 5,
    ) { index ->
        HomeStoryPagerItem(
            itemWidth = itemWidth,
            itemHeight = itemHeight,
            profileImage = painterResource(id = items[index].profileImage),
            storyImage = painterResource(id = items[index].storyImage),
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
        modifier = Modifier
            .size(width = itemWidth, height = itemHeight)
            .noRippleClickable(onClick),
    ) {
        HomeStoryHorizontalPagerItemMainImage(
            modifier = Modifier.zIndex(0f),
            itemWidth = itemWidth,
            itemHeight = itemHeight,
            image = storyImage,
        )
        HomeStoryHorizontalPagerItemProfileImage(
            modifier = Modifier
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
    modifier: Modifier,
    itemWidth: Dp,
    itemHeight: Dp,
    image: Painter,
) {
    Image(
        modifier = Modifier
            .size(width = itemWidth, height = itemHeight)
            .clip(RoundedCornerShape(16.dp))
            .then(modifier),
        contentScale = ContentScale.FillHeight,
        painter = image,
        contentDescription = "Story image",
    )
}

@Composable
private fun HomeStoryHorizontalPagerItemProfileImage(
    modifier: Modifier,
    onClick: () -> Unit,
    image: Painter,
    shouldShowBorder: Boolean,
) {
    val gradientBorder = Modifier
        .border(width = 2.dp, brush = defaultGradientBrush(), shape = CircleShape)
    val blackBorder = Modifier
        .border(width = 2.dp, color = Colors.PureBlack, shape = CircleShape)
    Image(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .then(if (shouldShowBorder) gradientBorder else Modifier)
            .padding(2.dp)
            .size(40.dp)
            .clip(CircleShape)
            .then(if (shouldShowBorder) blackBorder else Modifier)
            .then(modifier)
            .noRippleClickable(onClick),
        contentScale = ContentScale.Crop,
        painter = image,
        contentDescription = "Story profile image",
    )
}

@Composable
private fun HomeStoryHorizontalPagerItemDarkGradientBox(
    modifier: Modifier,
    itemWidth: Dp,
    itemHeight: Dp,
) {
    Box(
        modifier = Modifier
            .size(width = itemWidth, height = itemHeight)
            .clip(RoundedCornerShape(16.dp))
            .background(brush = slightDarkGradientBrush())
            .then(modifier),
    )
}
