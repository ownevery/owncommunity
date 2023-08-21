package com.gasparaitis.owncommunity.presentation.story

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalStdlibApi::class)
@Composable
fun StoryContent(
    state: StoryState,
    onAction: (StoryAction) -> Unit,
) {
    val pagerState = rememberPagerState { state.stories.size }
    var dominantColor by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .allowHardware(false)
                    .data("https://placebear.com/1000/1000")
                    .build(),
                onState = {
                    when (it) {
                        is AsyncImagePainter.State.Success -> {
                            val palette = Palette.from(it.result.drawable.toBitmap()).generate()
                            dominantColor = palette.dominantSwatch?.rgb?.toHexString().orEmpty()
                        }
                        else -> {
                            Log.d("justas", "failure.")
                        }
                    }
                }
            ),
            contentScale = ContentScale.FillWidth,
            contentDescription = "Image"
        )
//        StoryHorizontalPager(
//            stories = state.stories,
//            pagerState = pagerState,
//            onPageSelected = {},
//        )
    }
    LaunchedEffect(key1 = dominantColor) {
        Log.d("justas", "dominantColor = $dominantColor")
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
