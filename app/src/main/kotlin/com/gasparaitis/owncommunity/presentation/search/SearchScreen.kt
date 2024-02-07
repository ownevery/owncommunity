package com.gasparaitis.owncommunity.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
import com.gasparaitis.owncommunity.presentation.destinations.PostScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.ProfileScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.SearchScreenDestination
import com.gasparaitis.owncommunity.presentation.shared.composables.button.FollowButton
import com.gasparaitis.owncommunity.presentation.shared.composables.post.PostView
import com.gasparaitis.owncommunity.presentation.shared.composables.search.CustomTextField
import com.gasparaitis.owncommunity.presentation.shared.composables.tab.TabView
import com.gasparaitis.owncommunity.presentation.utils.extensions.humanReadableFollowerCountWithText
import com.gasparaitis.owncommunity.presentation.utils.modifier.noRippleClickable
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors
import com.gasparaitis.owncommunity.presentation.utils.theme.TextStyles
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    SearchContent(
        state = state,
        onAction = viewModel::onEvent,
    )
    SearchEventHandler(
        event = state.event,
        onEventHandled = viewModel::onEventHandled,
        navigator = navigator,
    )
}

@Composable
private fun SearchEventHandler(
    event: SearchState.Event?,
    onEventHandled: (SearchState.Event?) -> Unit,
    navigator: DestinationsNavigator,
) {
    LaunchedEffect(event) {
        when (event) {
            SearchState.NavigateToPostAuthorProfileScreen -> {
                navigator.navigate(ProfileScreenDestination) {
                    popUpTo(SearchScreenDestination) { saveState = true }
                }
            }
            SearchState.NavigateToPostScreen -> {
                navigator.navigate(PostScreenDestination) {
                    popUpTo(SearchScreenDestination) { saveState = true }
                }
            }
            SearchState.NavigateToProfileScreen -> {
                navigator.navigate(ProfileScreenDestination) {
                    popUpTo(SearchScreenDestination) { saveState = true }
                }
            }
            is SearchState.OnPostEvent -> {}
            is SearchState.OnProfileBodyClick -> {}
            is SearchState.OnProfileFollowButtonClick -> {}
            SearchState.OnSearchBarClick -> {}
            is SearchState.OnSearchBarQueryChange -> {}
            SearchState.OnSearchIconRepeatClick -> {}
            is SearchState.OnTabSelected -> {}
            SearchState.ScrollUp -> {}
            null -> {}
        }
        onEventHandled(event)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchContent(
    state: SearchState,
    onAction: (SearchState.Event) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val tabs =
        persistentListOf(
            stringResource(R.string.tab_title_trending),
            stringResource(R.string.tab_title_latest),
            stringResource(R.string.tab_title_people),
        )
    val pagerState =
        rememberPagerState(
            initialPage = state.selectedTabIndex,
        ) { tabs.size }
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBarView(
            searchText = state.searchText,
            onSearchBarClick = { onAction(SearchState.OnSearchBarClick) },
            onSearchQueryChange = { onAction(SearchState.OnSearchBarQueryChange(it)) },
        )
        TabView(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 12.dp),
            selectedTabIndex = state.selectedTabIndex,
            onTabSelected = { tabIndex ->
                scope.launch {
                    onAction(SearchState.OnTabSelected(tabIndex))
                    pagerState.animateScrollToPage(tabIndex)
                }
            },
            tabs = tabs,
        )
        SearchHorizontalPager(
            state = state,
            pagerState = pagerState,
            onTabSelected = { onAction(SearchState.OnTabSelected(it)) },
            onProfileClick = { onAction(SearchState.OnProfileBodyClick(it)) },
            onFollowButtonClick = { onAction(SearchState.OnProfileFollowButtonClick(it)) },
            onPostAction = { onAction(SearchState.OnPostEvent(it)) },
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
    onPostAction: (Post.Event) -> Unit,
) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        beyondBoundsPageCount = 2,
    ) { index ->
        when (index) {
            0 -> {
                PostListView(
                    posts = state.trendingPosts,
                    onPostEvent = onPostAction,
                )
            }
            1 -> {
                PostListView(
                    posts = state.latestPosts,
                    onPostEvent = onPostAction,
                )
            }
            2 -> {
                PeopleView(
                    profiles = state.profiles,
                    onProfileClick = onProfileClick,
                    onFollowButtonClick = onFollowButtonClick,
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
    posts: PersistentList<Post>,
    onPostEvent: (Post.Event) -> Unit,
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
                onAction = onPostEvent,
            )
        }
    }
}

@Composable
private fun PeopleView(
    profiles: PersistentList<Profile>,
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
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .then(modifier),
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.DarkGray,
        )
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.noRippleClickable(onProfileClick),
            ) {
                Image(
                    modifier =
                        Modifier
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
                        style =
                            TextStyles.body.copy(
                                fontWeight = FontWeight.Bold,
                                color = Colors.SocialWhite,
                                lineHeight = 16.sp,
                            ),
                    )
                    Text(
                        text = profile.followerCount.humanReadableFollowerCountWithText,
                        style =
                            TextStyles.secondary.copy(
                                color = Colors.SocialWhite,
                                lineHeight = 16.sp,
                            ),
                    )
                }
            }
            Spacer(Modifier.weight(1f))
            FollowButton(
                modifier = Modifier.size(height = 28.dp, width = 96.dp),
                isFollowed = profile.isFollowed,
                onClick = onFollowButtonClick,
            )
        }
        Spacer(Modifier.height(16.dp))
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
        enter =
            fadeIn() +
                expandVertically(
                    animationSpec = tween(durationMillis),
                ),
        exit =
            fadeOut() +
                shrinkVertically(
                    animationSpec = tween(durationMillis),
                ),
    ) {
        CustomTextField(
            modifier =
                Modifier
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
