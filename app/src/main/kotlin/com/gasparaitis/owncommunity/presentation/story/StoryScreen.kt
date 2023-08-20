package com.gasparaitis.owncommunity.presentation.story

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.gasparaitis.owncommunity.domain.shared.story.model.Story
import com.gasparaitis.owncommunity.presentation.destinations.StoryScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.flow.distinctUntilChanged
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
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        StoryHorizontalPager(
            stories = state.stories,
            pagerState = pagerState,
            onPageSelected = {},
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StoryHorizontalPager(
    stories: List<Story>,
    pagerState: PagerState,
    onPageSelected: (Int) -> Unit,
) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        pageCount = stories.size,
        state = pagerState,
        beyondBoundsPageCount = 2,
    ) { index ->
        StoryView(
            story = stories[index],
        )
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
) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = story.storyImages.first()),
        contentDescription = "Story image"
    )
}
