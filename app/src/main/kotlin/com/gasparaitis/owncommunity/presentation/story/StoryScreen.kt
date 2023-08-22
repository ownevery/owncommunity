package com.gasparaitis.owncommunity.presentation.story

import android.graphics.drawable.Drawable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
import com.gasparaitis.owncommunity.domain.shared.story.model.Story
import com.gasparaitis.owncommunity.presentation.destinations.StoryScreenDestination
import com.gasparaitis.owncommunity.presentation.shared.composables.story.StoryProfileImage
import com.gasparaitis.owncommunity.presentation.utils.extensions.humanReadableFollowerCount
import com.gasparaitis.owncommunity.presentation.utils.extensions.offsetForPage
import com.gasparaitis.owncommunity.presentation.utils.extensions.verticalBackgroundGradientBrush
import com.gasparaitis.owncommunity.presentation.utils.modifier.noRippleClickable
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors
import com.gasparaitis.owncommunity.presentation.utils.theme.TextStyles
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Destination
@Composable
fun StoryScreen(
    navigator: DestinationsNavigator,
    viewModel: StoryViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState()
    StoryContent(
        state = state.value,
        onAction = viewModel::onAction,
    )
    LaunchedEffect(Unit) {
        viewModel.navEvent.collect {
            navigator.navigate(it.destination) {
                popUpTo(StoryScreenDestination) { saveState = true }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoryContent(
    state: StoryState,
    onAction: (StoryAction) -> Unit,
) {
    val pagerState = rememberPagerState { state.stories.size }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        StoryHorizontalPager(
            stories = state.stories,
            pagerState = pagerState,
            onPageSelected = {},
            onProfileClick = { onAction(StoryAction.OnProfileClick(it)) }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StoryHorizontalPager(
    stories: List<Story>,
    pagerState: PagerState,
    onPageSelected: (Int) -> Unit,
    onProfileClick: (Profile) -> Unit,
) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        beyondBoundsPageCount = 2,
    ) { index ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .graphicsLayer {
                    val pageOffset = pagerState.offsetForPage(index)
                    val offScreenRight = pageOffset < 0f
                    val deg = 105f
                    val interpolated = FastOutLinearInEasing.transform(pageOffset.absoluteValue)
                    rotationY = min(interpolated * if (offScreenRight) deg else -deg, 90f)
                    transformOrigin = TransformOrigin(
                        pivotFractionX = if (offScreenRight) 0f else 1f,
                        pivotFractionY = .5f
                    )
                }
        ) {
            StoryView(
                story = stories[index],
                onProfileClick = { onProfileClick(stories[index].profile) }
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
) {
    var index by remember { mutableIntStateOf(0) }
    StoryViewItem(
        story = story,
        onFirstHalfClick = {
            index = index.dec().coerceIn(0, story.storyImages.size.dec())
        },
        onSecondHalfClick = {
            index = index.inc().coerceIn(0, story.storyImages.size.dec())
        },
        onProfileClick = onProfileClick,
        drawable = ContextCompat.getDrawable(
            LocalContext.current,
            story.storyImages[index]
        )!!
    )
}

@Composable
private fun StoryViewItem(
    story: Story,
    drawable: Drawable,
    onFirstHalfClick: () -> Unit,
    onSecondHalfClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StoryViewItemBackgroundBox(
            modifier = Modifier.zIndex(0f),
            drawable = drawable
        )
        StoryImage(
            modifier = Modifier.zIndex(1f),
            drawable = drawable
        )
        LinearIndicator()
        StoryProfileRow(
            modifier = Modifier.zIndex(2f),
            story = story,
            onClick = onProfileClick,
        )
        HalfWidthBox(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onFirstHalfClick
        )
        HalfWidthBox(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = onSecondHalfClick
        )
    }
}

@Composable
fun StoryProfileRow(
    modifier: Modifier = Modifier,
    story: Story,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.noRippleClickable(onClick)
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
                style = TextStyles.body.copy(
                    fontWeight = FontWeight.Bold,
                    color = Colors.SocialWhite,
                    lineHeight = 16.sp,
                ),
            )
            Text(
                text = story.profile.followerCount.humanReadableFollowerCount,
                style = TextStyles.secondary.copy(
                    color = Colors.SocialWhite,
                    lineHeight = 16.sp,
                ),
            )
        }
    }
}

@Composable
private fun HalfWidthBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.5f)
            .then(modifier)
            .noRippleClickable(onClick = onClick)
    )
}

@Composable
private fun StoryImage(
    modifier: Modifier = Modifier,
    drawable: Drawable,
) {
    Image(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        painter = rememberDrawablePainter(drawable = drawable),
        contentDescription = "Story image"
    )
}

@Composable
private fun StoryViewItemBackgroundBox(
    modifier: Modifier = Modifier,
    drawable: Drawable,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = drawable.verticalBackgroundGradientBrush)
            .then(modifier)
    )
}

@Composable
fun LinearIndicatorRow(
    count: Int = 5,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        repeat(count) {
            LinearIndicator(
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun LinearIndicator(
    modifier: Modifier = Modifier,
    indicatorBackgroundColor: Color = Colors.DarkBlack,
    indicatorProgressColor: Color = Colors.PureWhite,
    slideDurationInSeconds: Long = 5L,
    onAnimationEnd: () -> Unit = {},
    onPauseTimer: Boolean = false,
    hideIndicators: Boolean = false,
) {
    val delayInMillis = rememberSaveable { (slideDurationInSeconds * 1000) / 100 }
    var progress by remember { mutableFloatStateOf(0.00f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = ""
    )
    if (hideIndicators.not()) {
        LinearProgressIndicator(
            backgroundColor = indicatorBackgroundColor,
            color = indicatorProgressColor,
            modifier = modifier
                .padding(top = 12.dp, bottom = 12.dp)
                .clip(RoundedCornerShape(12.dp)),
            progress = animatedProgress,
        )
    }
    LaunchedEffect(key1 = onPauseTimer) {
        while (progress < 1f && isActive && onPauseTimer.not()) {
            progress += 0.01f
            delay(delayInMillis)
        }

        if (onPauseTimer.not()) {
            delay(200)
            onAnimationEnd()
        }
    }
}
