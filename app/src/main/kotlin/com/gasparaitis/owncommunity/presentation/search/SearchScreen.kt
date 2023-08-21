package com.gasparaitis.owncommunity.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
import com.gasparaitis.owncommunity.presentation.destinations.SearchScreenDestination
import com.gasparaitis.owncommunity.presentation.shared.composables.post.PostAction
import com.gasparaitis.owncommunity.presentation.shared.composables.post.PostView
import com.gasparaitis.owncommunity.presentation.shared.composables.search.CustomTextField
import com.gasparaitis.owncommunity.presentation.utils.extensions.customTabIndicatorOffset
import com.gasparaitis.owncommunity.presentation.utils.extensions.humanReadableFollowerCount
import com.gasparaitis.owncommunity.presentation.utils.modifier.noRippleClickable
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors
import com.gasparaitis.owncommunity.presentation.utils.theme.TextStyles
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState()
    SearchContent(
        state = state.value,
        onAction = viewModel::onAction,
    )
    LaunchedEffect(Unit) {
        viewModel.navEvent.collect {
            if (it is SearchNavEvent.ScrollUp) {
                return@collect
            }
            navigator.navigate(it.destination) {
                popUpTo(SearchScreenDestination) { saveState = true }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchContent(
    state: SearchState,
    onAction: (SearchAction) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val tabs = listOf(
        stringResource(R.string.tab_title_trending),
        stringResource(R.string.tab_title_latest),
        stringResource(R.string.tab_title_people),
    )
    val pagerState = rememberPagerState(
        initialPage = state.selectedTabIndex,
    ) { tabs.size }
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBarView(
            searchText = state.searchText,
            onSearchBarClick = { onAction(SearchAction.OnSearchBarClick) },
            onSearchQueryChange = { onAction(SearchAction.OnSearchBarQueryChange(it)) },
        )
        TabView(
            selectedTabIndex = state.selectedTabIndex,
            onTabSelected = { tabIndex ->
                scope.launch {
                    onAction(SearchAction.OnTabSelected(tabIndex))
                    pagerState.animateScrollToPage(tabIndex)
                }
            },
            tabs = tabs,
        )
        SearchHorizontalPager(
            state = state,
            pagerState = pagerState,
            onTabSelected = { onAction(SearchAction.OnTabSelected(it)) },
            onProfileClick = { onAction(SearchAction.OnProfileBodyClick(it)) },
            onFollowButtonClick = { onAction(SearchAction.OnProfileFollowButtonClick(it)) },
            onPostAction = { onAction(SearchAction.OnPostAction(it)) },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchHorizontalPager(
    state: SearchState,
    pagerState: PagerState,
    onTabSelected: (Int) -> Unit,
    onProfileClick: (Profile) -> Unit,
    onFollowButtonClick: (Profile) -> Unit,
    onPostAction: (PostAction) -> Unit,
) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        beyondBoundsPageCount = 2
    ) { index ->
        when (index) {
            0 -> {
                PostListView(
                    posts = state.trendingPosts,
                    onPostAction = onPostAction,
                )
            }
            1 -> {
                PostListView(
                    posts = state.latestPosts,
                    onPostAction = onPostAction,
                )
            }
            2 -> {
                PeopleView(
                    profiles = state.profiles,
                    onProfileClick = onProfileClick,
                    onFollowButtonClick = onFollowButtonClick
                )
            }
        }
    }
    LaunchedEffect(key1 = pagerState) {
        this.launch {
            snapshotFlow { pagerState.currentPage }.distinctUntilChanged().collect { tabIndex ->
                onTabSelected(tabIndex)
            }
        }
    }
}

@Composable
private fun PostListView(
    posts: List<Post>,
    onPostAction: (PostAction) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
    ) {
        items(
            count = posts.size,
            key = { posts[it].id },
        ) { index ->
            PostView(
                item = posts[index],
                onAction = onPostAction,
            )
        }
    }
}

@Composable
private fun PeopleView(
    profiles: List<Profile>,
    onProfileClick: (Profile) -> Unit,
    onFollowButtonClick: (Profile) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
    ) {
        items(
            count = profiles.size,
            key = { profiles[it].id },
        ) { index ->
            ProfileView(
                profile = profiles[index],
                onProfileClick = { onProfileClick(profiles[index]) },
                onFollowButtonClick = { onFollowButtonClick(profiles[index]) },
            )
        }
    }
}

@Composable
private fun ProfileView(
    profile: Profile,
    onProfileClick: () -> Unit,
    onFollowButtonClick: () -> Unit,
) {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = Color.DarkGray,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.noRippleClickable(onProfileClick)
        ) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                painter = painterResource(id = profile.profileImage),
                contentScale = ContentScale.Crop,
                contentDescription = "Profile image",
            )
            Column(
                modifier = Modifier.padding(start = 8.dp),
            ) {
                Text(
                    text = profile.displayName,
                    style = TextStyles.body.copy(
                        fontWeight = FontWeight.Bold,
                        color = Colors.SocialWhite,
                        lineHeight = 16.sp,
                    ),
                )
                Text(
                    text = profile.followerCount.humanReadableFollowerCount,
                    style = TextStyles.secondary.copy(
                        color = Colors.SocialWhite,
                        lineHeight = 16.sp,
                    ),
                )
            }
        }
        Spacer(Modifier.weight(1f))
        FollowButton(
            isFollowed = profile.isFollowed,
            onClick = onFollowButtonClick,
        )
    }
    Spacer(Modifier.height(16.dp))
}

@Composable
private fun FollowButton(
    isFollowed: Boolean,
    onClick: () -> Unit,
) {
    if (isFollowed) {
        Button(
            modifier = Modifier.size(height = 28.dp, width = 96.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Colors.SocialPink,
                contentColor = Colors.PureWhite,
            ),
            contentPadding = PaddingValues(),
        ) {
            Text(
                text = stringResource(R.string.follow_button_follow),
                style = TextStyles.secondary.copy(
                    color = Colors.PureWhite,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 16.sp,
                )
            )
        }
    } else {
        Button(
            modifier = Modifier.size(height = 28.dp, width = 96.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Colors.DarkBlack,
                contentColor = Colors.PureWhite,
            ),
            border = BorderStroke(width = 1.dp, color = Colors.LightGray),
            contentPadding = PaddingValues(),
        ) {
            Text(
                text = stringResource(R.string.follow_button_following),
                style = TextStyles.secondary.copy(
                    color = Colors.PureWhite,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 16.sp,
                )
            )
        }
    }
}


@Composable
private fun SearchBarView(
    searchText: TextFieldValue,
    onSearchQueryChange: (String) -> Unit,
    onSearchBarClick: () -> Unit,
    durationMillis: Int = 500,
) {
    val isVisible by remember { mutableStateOf(true) }
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + expandVertically(
            animationSpec = tween(durationMillis),
        ),
        exit = fadeOut() + shrinkVertically(
            animationSpec = tween(durationMillis),
        ),
    ) {
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp)
                .height(48.dp),
            value = searchText.text,
            onValueChange = onSearchQueryChange,
            onTextFieldClick = onSearchBarClick,
            textStyle = TextStyles.secondary,
            placeholderText = {
                Text(
                    text = stringResource(id = R.string.search_search_bar_placeholder),
                    style = TextStyles.secondary,
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.Search,
                    tint = Colors.LightGray,
                    contentDescription = "Search",
                )
            },
        )
    }
}

@Composable
private fun TabView(
    selectedTabIndex: Int,
    tabs: List<String>,
    onTabSelected: (Int) -> Unit
) {
    val density = LocalDensity.current
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }
    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .padding(horizontal = 12.dp),
        selectedTabIndex = selectedTabIndex,
        divider = {},
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.customTabIndicatorOffset(
                    currentTabPosition = tabPositions[selectedTabIndex],
                    tabWidth = tabWidths[selectedTabIndex]
                ),
                color = Colors.SocialBlue,
                height = 4.dp,
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        style = TextStyles.secondary.copy(
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            lineHeight = 16.sp,
                        ),
                        onTextLayout = { textLayoutResult ->
                            tabWidths[index] = with(density) { textLayoutResult.size.width.toDp() }
                        }
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun FollowButtonPreview() {
    Row {
        FollowButton(
            isFollowed = false,
            onClick = {},
        )
        FollowButton(
            isFollowed = true,
            onClick = {},
        )
    }
}
