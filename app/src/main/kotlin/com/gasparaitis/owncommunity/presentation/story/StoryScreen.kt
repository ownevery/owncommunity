package com.gasparaitis.owncommunity.presentation.story

import android.graphics.drawable.Drawable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
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
import com.gasparaitis.owncommunity.presentation.shared.composables.story.CircleProfileImage
import com.gasparaitis.owncommunity.presentation.utils.extensions.humanReadableFollowerCountWithText
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
            is StoryState.OnPageSelected -> {}
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
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        StoryHorizontalPager(
            stories = state.stories,
            pagerState = pagerState,
            onPageSelected = { onAction(StoryState.OnPageSelected(it)) },
            onProfileClick = { onAction(StoryState.OnProfileClick(it)) },
            onBack = { storyIndex, scrollToPage ->
                onAction(StoryState.OnStoryGoBack(storyIndex, scrollToPage))
            },
            onForward = { storyIndex, scrollToPage ->
                onAction(StoryState.OnStoryGoForward(storyIndex, scrollToPage))
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
    onBack: (storyIndex: Int, scrollToPage: suspend (Int) -> Unit) -> Unit,
    onForward: (storyIndex: Int, scrollToPage: suspend (Int) -> Unit) -> Unit,
) {
    val scrollToPage: suspend (Int) -> Unit = { page ->
        pagerState.scrollToPage(page)
    }
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        beyondBoundsPageCount = 1,
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
                isFocused = index == pagerState.currentPage,
                story = stories[index],
                onProfileClick = { onProfileClick(stories[index].profile) },
                onBack = { onBack(index, scrollToPage) },
                onForward = { onForward(index, scrollToPage) },
            )
        }
    }
    LaunchedEffect(key1 = pagerState) {
        launch {
            snapshotFlow { pagerState.currentPage }.distinctUntilChanged().collect { tabIndex ->
                onPageSelected(tabIndex)
            }
        }
    }
}

@Composable
private fun StoryView(
    isFocused: Boolean,
    story: Story,
    onProfileClick: () -> Unit,
    onBack: () -> Unit,
    onForward: () -> Unit,
) {
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
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(top = 4.dp)
                    .padding(horizontal = 24.dp),
        ) {
            LinearIndicatorRow(
                story = story,
                isFocused = isFocused,
                isPaused = isPaused,
                onAnimationEnd = onForward,
            )
            StoryProfileRow(
                story = story,
                onClick = onProfileClick,
            )
        }
        StoryViewItem(
            onHold = { isReleased ->
                isPaused = !isReleased
            },
            onFirstHalfTap = onBack,
            onSecondHalfTap = onForward,
            drawable =
                ContextCompat.getDrawable(
                    LocalContext.current,
                    story.entries[story.entryIndex].drawableResId,
                )!!,
        )
    }
}

@Composable
private fun StoryTopGradient(modifier: Modifier = Modifier,) {
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
private fun StoryProfileRow(
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
        CircleProfileImage(
            modifier =
                Modifier
                    .align(Alignment.CenterVertically)
                    .zIndex(2f),
            padding = PaddingValues(bottom = 8.dp),
            onClick = onClick,
            image = painterResource(id = story.profile.profileImage),
            isBorderShown = !story.isRead,
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
                text = story.profile.followerCount.humanReadableFollowerCountWithText,
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
private fun LinearIndicatorRow(
    story: Story,
    isFocused: Boolean,
    isPaused: Boolean,
    onAnimationEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        repeat(story.entries.size) { index ->
            LinearIndicator(
                modifier = Modifier.weight(1f),
                onAnimationEnd = onAnimationEnd,
                isActive = isFocused && index == story.entryIndex,
                isCompleted = index < story.entryIndex,
                isPaused = isPaused,
            )
        }
    }
}

@Composable
private fun LinearIndicator(
    modifier: Modifier = Modifier,
    durationMillis: Int = 2500,
    isActive: Boolean = false,
    isCompleted: Boolean = false,
    isPaused: Boolean = false,
    onAnimationEnd: () -> Unit = {},
) {
    var isLaunched by remember { mutableStateOf(false) }
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
            Modifier
                .padding(top = 12.dp, bottom = 12.dp)
                .clip(RoundedCornerShape(12.dp))
                .then(modifier),
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
        if (isActive && !isCompleted) {
            launchAnimation()
        } else {
            progress.snapTo(0f)
        }
    }
    LaunchedEffect(key1 = isPaused) {
        @Suppress("KotlinConstantConditions")
        if (isActive && isPaused) {
            progress.stop()
        } else if (!isLaunched) {
            isLaunched = true
        } else if (isActive && !isPaused && isLaunched) {
            launchAnimation()
        }
    }
    LaunchedEffect(key1 = progress.value) {
        if (progress.value >= 1.0f) {
            onAnimationEnd()
        }
    }
}
