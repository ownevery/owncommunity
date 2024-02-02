package com.gasparaitis.owncommunity.presentation.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.domain.shared.comment.Comment
import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
import com.gasparaitis.owncommunity.presentation.shared.composables.button.BookmarkIconButton
import com.gasparaitis.owncommunity.presentation.shared.composables.button.ChatIconButton
import com.gasparaitis.owncommunity.presentation.shared.composables.button.EditProfileButton
import com.gasparaitis.owncommunity.presentation.shared.composables.post.PostView
import com.gasparaitis.owncommunity.presentation.shared.composables.story.CircleProfileImage
import com.gasparaitis.owncommunity.presentation.shared.composables.tab.TabView
import com.gasparaitis.owncommunity.presentation.utils.extensions.humanReadableActionCount
import com.gasparaitis.owncommunity.presentation.utils.preview.ScreenPreview
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors
import com.gasparaitis.owncommunity.presentation.utils.theme.TextStyles
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Destination
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    ProfileContent(
        state = state,
        onAction = viewModel::onEvent,
    )
    ProfileEventHandler(
        event = state.event,
        onEventHandled = viewModel::onEventHandled,
        navigator = navigator,
    )
}

@Composable
private fun ProfileEventHandler(
    event: ProfileState.Event?,
    onEventHandled: (ProfileState.Event?) -> Unit,
    navigator: DestinationsNavigator,
) {
    LaunchedEffect(event) {
        when (event) {
            ProfileState.NavigateToBookmarksScreen -> {}
            ProfileState.NavigateToChatListScreen -> {}
            ProfileState.NavigateToEditProfileScreen -> {}
            ProfileState.OnBookmarkButtonClick -> {}
            ProfileState.OnChatButtonClick -> {}
            ProfileState.OnEditProfileButtonClick -> {}
            is ProfileState.OnPostEvent -> {}
            is ProfileState.OnTabSelected -> {}
            null -> {}
            ProfileState.NavigateToPostAuthorProfileScreen -> {}
            ProfileState.NavigateToPostScreen -> {}
        }
        onEventHandled(event)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfileContent(
    state: ProfileState,
    onAction: (ProfileState.Event) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val tabs =
        persistentListOf(
            stringResource(R.string.tab_title_posts),
            stringResource(R.string.tab_title_replies),
            stringResource(R.string.tab_title_popular),
            stringResource(R.string.tab_title_likes),
        )
    val pagerState =
        rememberPagerState(
            initialPage = state.selectedTabIndex,
        ) { tabs.size }
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        ProfileTopView(
            profile = state.profile,
            onChatButtonClick = { onAction(ProfileState.OnChatButtonClick) },
            onBookmarkButtonClick = { onAction(ProfileState.OnBookmarkButtonClick) },
            onEditProfileButtonClick = { onAction(ProfileState.OnEditProfileButtonClick) },
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
                    onAction(ProfileState.OnTabSelected(tabIndex))
                    pagerState.animateScrollToPage(tabIndex)
                }
            },
            tabs = tabs,
        )
        ProfileHorizontalPager(
            latestPosts = state.latestPosts,
            likedPosts = state.likedPosts,
            popularPosts = state.popularPosts,
            replies = state.replies,
            pagerState = pagerState,
            onTabSelected = { onAction(ProfileState.OnTabSelected(it)) },
            onPostEvent = { onAction(ProfileState.OnPostEvent(it)) },
        )
    }
}

@Composable
private fun ProfileTopView(
    profile: Profile,
    modifier: Modifier = Modifier,
    onChatButtonClick: () -> Unit = {},
    onBookmarkButtonClick: () -> Unit = {},
    onEditProfileButtonClick: () -> Unit = {},
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .then(modifier),
    ) {
        CoverImage(
            painter = painterResource(id = R.drawable.demo_my_profile_cover),
        )
        CoverImageActionColumn(
            onChatButtonClick = onChatButtonClick,
            onBookmarkButtonClick = onBookmarkButtonClick
        )
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileImage(
                painter = painterResource(id = R.drawable.demo_my_profile_photo),
            )
            ProfileDisplayName(
                text = profile.displayName,
            )
            ProfileLocationName(
                text = profile.locationName,
            )
            ProfileShortBiography(
                text = profile.shortBiography,
            )
            ProfileFollowRow(
                followerCount = profile.followerCount,
                followingCount = profile.followingCount,
                onEditProfileButtonClick = onEditProfileButtonClick,
            )
        }
    }
}

@Composable
private fun BoxScope.CoverImageActionColumn(
    onChatButtonClick: () -> Unit,
    onBookmarkButtonClick: () -> Unit
) {
    Column(
        modifier =
        Modifier
            .align(Alignment.TopEnd)
            .padding(top = 56.dp, end = 24.dp),
    ) {
        ChatIconButton(
            modifier = Modifier.padding(bottom = 20.dp),
            isBadgeEnabled = true,
            onClick = onChatButtonClick,
        )
        BookmarkIconButton(
            onClick = onBookmarkButtonClick,
        )
    }
}

@Composable
private fun ProfileFollowRow(
    followerCount: Long,
    followingCount: Long,
    onEditProfileButtonClick: () -> Unit
) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = followerCount.humanReadableActionCount,
                style =
                TextStyles.secondary.copy(
                    color = Colors.SocialWhite,
                    lineHeight = 16.sp,
                ),
            )
            Text(
                text = stringResource(R.string.profile_followers),
                style = TextStyles.secondary,
                color = Colors.LightGray,
            )
        }
        Column {
            Text(
                text = followingCount.humanReadableActionCount,
                style =
                TextStyles.secondary.copy(
                    color = Colors.SocialWhite,
                    lineHeight = 16.sp,
                ),
            )
            Text(
                text = stringResource(R.string.profile_following),
                style = TextStyles.secondary,
                color = Colors.LightGray,
            )
        }
        EditProfileButton(
            modifier = Modifier.size(width = 138.dp, height = 36.dp),
            onClick = onEditProfileButtonClick,
        )
    }
}

@Composable
private fun ProfileShortBiography(text: String,) {
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = text,
        style = TextStyles.body,
    )
}

@Composable
private fun ProfileLocationName(text: String,) {
    Text(
        modifier = Modifier.padding(2.dp),
        text = text,
        style = TextStyles.secondary,
        color = Colors.LightGray,
    )
}

@Composable
private fun ProfileDisplayName(text: String,) {
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = text,
        style =
            TextStyles.title.copy(
                lineHeight = 24.sp,
            ),
    )
}

@Composable
private fun ProfileImage(painter: Painter,) {
    CircleProfileImage(
        modifier =
            Modifier
                .zIndex(1f),
        padding = PaddingValues(top = 84.dp),
        image = painter,
        size = 142.dp,
    )
}

@Composable
private fun BoxScope.CoverImage(
    painter: Painter,
) {
    Image(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(160.dp),
        painter = painter,
        contentDescription = "Cover image",
        contentScale = ContentScale.Crop,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfileHorizontalPager(
    latestPosts: PersistentList<Post>,
    likedPosts: PersistentList<Post>,
    popularPosts: PersistentList<Post>,
    replies: PersistentList<Comment>,
    pagerState: PagerState,
    onTabSelected: (Int) -> Unit,
    onPostEvent: (Post.Event) -> Unit,
) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        beyondBoundsPageCount = 2,
    ) { index ->
        when (index) {
            0 -> {
                PostListView(
                    posts = latestPosts,
                    onPostEvent = onPostEvent,
                )
            }
            1 -> {
                CommentListView(
                    comments = replies,
                    onCommentEvent = onPostEvent,
                )
            }
            2 -> {
                PostListView(
                    posts = popularPosts,
                    onPostEvent = onPostEvent,
                )
            }
            3 -> {
                PostListView(
                    posts = likedPosts,
                    onPostEvent = onPostEvent,
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
private fun CommentListView(
    comments: PersistentList<Comment>,
    onCommentEvent: (Post.Event) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
    ) {
        items(
            count = comments.size,
            key = { comments[it].id },
        ) { index ->
            Spacer(Modifier.height(100.dp))
        }
    }
}

@ScreenPreview
@Composable
private fun ProfileContentPreview() {
    ProfileContent(
        state = ProfileState.EMPTY,
        onAction = {},
    )
}
