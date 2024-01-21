package com.gasparaitis.owncommunity.presentation.story

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
import com.gasparaitis.owncommunity.domain.shared.story.model.Story
import com.gasparaitis.owncommunity.presentation.destinations.ProfileScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.StoryScreenDestination
import com.gasparaitis.owncommunity.presentation.shared.composables.story.StoryProfileImage
import com.gasparaitis.owncommunity.presentation.utils.extensions.humanReadableFollowerCount
import com.gasparaitis.owncommunity.presentation.utils.extensions.verticalBackgroundGradientBrush
import com.gasparaitis.owncommunity.presentation.utils.modifier.noRippleClickable
import com.gasparaitis.owncommunity.presentation.utils.modifier.pagerCubeTransition
import com.gasparaitis.owncommunity.presentation.utils.modifier.storyPointerInput
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors
import com.gasparaitis.owncommunity.presentation.utils.theme.TextStyles
import com.gasparaitis.owncommunity.presentation.utils.theme.slightTopDarkGradientBrush
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Destination
@Composable
fun StoryScreen(
    navigator: DestinationsNavigator,
    viewModel: StoryViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    StoryContent(
        state = state,
        onAction = viewModel::onEvent,
    )
    StoryEventHandler(
        event = state.event,
        onEventHandled = viewModel::onEventHandled,
        navigator = navigator,
    )
}

@Composable
private fun StoryEventHandler(
    event: StoryState.Event?,
    onEventHandled: (StoryState.Event?) -> Unit,
    navigator: DestinationsNavigator,
) {
    LaunchedEffect(event) {
        when (event) {
            StoryState.NavigateToProfileScreen -> {
                navigator.navigate(ProfileScreenDestination) {
                    popUpTo(StoryScreenDestination) { saveState = true }
                }
            }
            is StoryState.OnProfileClick -> {}
            is StoryState.OnStoryGoBack -> {}
            is StoryState.OnStoryGoForward -> {}
            StoryState.OnStoryReplyBarClick -> {}
            is StoryState.OnStoryReplyQueryChange -> {}
            StoryState.OnStoryReplySend -> {}
            is StoryState.OnTabSelected -> {}
            null -> {}
        }
        onEventHandled(event)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StoryContent(
    state: StoryState,
    onAction: (StoryState.Event) -> Unit,
) {
    val pagerState = rememberPagerState { state.stories.size }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        StoryHorizontalPager(
            stories = state.stories,
            pagerState = pagerState,
            onPageSelected = {},
            onProfileClick = { onAction(StoryState.OnProfileClick(it)) },
            onBack = {
                    storyIndex,
                    storyItemIndex ->
                onAction(StoryState.OnStoryGoBack(storyIndex, storyItemIndex))
            },
            onForward = {
                    storyIndex,
                    storyItemIndex ->
                onAction(StoryState.OnStoryGoForward(storyIndex, storyItemIndex))
            },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StoryHorizontalPager(
    stories: PersistentList<Story>,
    pagerState: PagerState,
    onPageSelected: (Int) -> Unit,
    onProfileClick: (Profile) -> Unit,
    onBack: (Int, Int) -> Unit,
    onForward: (Int, Int) -> Unit,
) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        beyondBoundsPageCount = 2,
    ) { index ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .pagerCubeTransition(
                        pagerState = pagerState,
                        index = index,
                    ),
        ) {
            StoryView(
                story = stories[index],
                onProfileClick = { onProfileClick(stories[index].profile) },
                onBack = { itemIndex -> onBack(index, itemIndex) },
                onForward = { itemIndex -> onForward(index, itemIndex) },
            )
        }
    }
    LaunchedEffect(key1 = pagerState) {
        this.launch {
            snapshotFlow { pagerState.currentPage }.distinctUntilChanged().collect { tabIndex ->
                onPageSelected(tabIndex)
            }
        }
    }
}

@Composable
private fun StoryView(
    story: Story,
    onProfileClick: () -> Unit,
    onBack: (Int) -> Unit,
    onForward: (Int) -> Unit,
) {
    var index by remember { mutableIntStateOf(0) }
    var isPaused by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        StoryTopGradient(
            modifier = Modifier.zIndex(2f),
        )
        Column(
            modifier =
                Modifier
                    .zIndex(3f)
                    .padding(top = 8.dp)
                    .padding(horizontal = 24.dp),
        ) {
            LinearIndicatorRow(
                story = story,
                currentIndex = index,
                onAnimationEnd = { index = index.inc().coerceIn(0, story.storyEntries.size.dec()) },
                isPaused = isPaused,
            )
            StoryProfileRow(
                story = story,
                onClick = onProfileClick,
            )
        }
        StoryViewItem(
            onHold = { isReleased ->
                isPaused = !isReleased
                Log.d("justas", "onHold($isReleased)")
            },
            onFirstHalfTap = {
                index = index.dec().coerceIn(0, story.storyEntries.size.dec())
                onBack(index)
                Log.d("justas", "onFirstHalfTap")
            },
            onSecondHalfTap = {
                onForward(index)
                index = index.inc().coerceIn(0, story.storyEntries.size.dec())
                Log.d("justas", "onSecondHalfTap")
            },
            drawable =
                ContextCompat.getDrawable(
                    LocalContext.current,
                    story.storyEntries[index].drawableResId,
                )!!,
        )
    }
}

@Composable
fun StoryTopGradient(modifier: Modifier = Modifier,) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(brush = slightTopDarkGradientBrush())
                .then(modifier),
    )
}

@Composable
private fun StoryViewItem(
    drawable: Drawable,
    onHold: (Boolean) -> Unit,
    onFirstHalfTap: () -> Unit,
    onSecondHalfTap: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .storyPointerInput(
                    onFirstHalfTap = onFirstHalfTap,
                    onSecondHalfTap = onSecondHalfTap,
                    onHold = onHold,
                ),
    ) {
        StoryViewItemBackgroundBox(
            modifier = Modifier.zIndex(0f),
            drawable = drawable,
        )
        StoryImage(
            modifier = Modifier.zIndex(1f),
            drawable = drawable,
        )
    }
}

@Composable
fun StoryProfileRow(
    story: Story,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            Modifier
                .padding(top = 12.dp)
                .then(modifier)
                .noRippleClickable { onClick() },
        verticalAlignment = Alignment.Top,
    ) {
        StoryProfileImage(
            onClick = onClick,
            image = painterResource(id = story.profile.profileImage),
            shouldShowBorder = !story.isRead,
        )
        Column(
            modifier = Modifier.padding(start = 8.dp),
        ) {
            Text(
                text = story.profile.displayName,
                style =
                    TextStyles.body.copy(
                        fontWeight = FontWeight.Bold,
                        color = Colors.SocialWhite,
                        lineHeight = 16.sp,
                    ),
            )
            Text(
                text = story.profile.followerCount.humanReadableFollowerCount,
                style =
                    TextStyles.secondary.copy(
                        color = Colors.SocialWhite,
                        lineHeight = 16.sp,
                    ),
            )
        }
    }
}

@Composable
private fun StoryImage(
    drawable: Drawable,
    modifier: Modifier = Modifier,
) {
    Image(
        modifier =
            Modifier
                .fillMaxSize()
                .then(modifier),
        painter = rememberDrawablePainter(drawable = drawable),
        contentDescription = "Story image",
    )
}

@Composable
private fun StoryViewItemBackgroundBox(
    drawable: Drawable,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(brush = drawable.verticalBackgroundGradientBrush)
                .then(modifier),
    )
}

@Composable
fun LinearIndicatorRow(
    story: Story,
    currentIndex: Int,
    onAnimationEnd: () -> Unit,
    isPaused: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        repeat(story.storyEntries.size) { index ->
            LinearIndicator(
                modifier = Modifier.weight(1f),
                onAnimationEnd = onAnimationEnd,
                isActive = currentIndex == index,
                isCompleted = story.storyEntries[index].isSeen,
                isPaused = isPaused,
            )
        }
    }
}

@Composable
private fun LinearIndicator(
    modifier: Modifier = Modifier,
    durationMillis: Int = 5000,
    onAnimationEnd: () -> Unit = {},
    isActive: Boolean = false,
    isCompleted: Boolean = false,
    isPaused: Boolean = false,
) {
    val progress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    fun launchAnimation() {
        scope.launch {
            progress.animateTo(
                targetValue = 1f,
                animationSpec =
                    tween(
                        durationMillis = durationMillis,
                        delayMillis = 0,
                        easing = LinearEasing,
                    ),
            )
        }
    }
    LinearProgressIndicator(
        backgroundColor = Colors.DarkBlack,
        color = Colors.PureWhite,
        modifier =
            modifier
                .padding(top = 12.dp, bottom = 12.dp)
                .clip(RoundedCornerShape(12.dp)),
        progress =
            if (isCompleted) {
                1.0f
            } else if (!isActive) {
                0.0f
            } else {
                progress.value
            },
    )
    LaunchedEffect(key1 = isActive) {
        if (isActive && isCompleted) {
            onAnimationEnd()
        } else if (isActive) {
            launchAnimation()
        } else {
            progress.snapTo(0f)
        }
    }
    LaunchedEffect(key1 = isPaused) {
        if (isActive && isPaused) {
            progress.stop()
        } else if (isActive && !isPaused) {
            launchAnimation()
        }
    }
}
