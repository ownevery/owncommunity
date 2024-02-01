package com.gasparaitis.owncommunity.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
import com.gasparaitis.owncommunity.presentation.shared.composables.button.BookmarkIconButton
import com.gasparaitis.owncommunity.presentation.shared.composables.button.ChatIconButton
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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

@Composable
private fun ProfileContent(
    state: ProfileState,
    onAction: (ProfileState.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        ProfileTopView(
            profile = state.profile,
            onChatButtonClick = { onAction(ProfileState.OnChatButtonClick) },
            onBookmarkButtonClick = { onAction(ProfileState.OnBookmarkButtonClick) },
            onEditProfileButtonClick = { onAction(ProfileState.OnEditProfileButtonClick) },
        )
        ProfileTabView()
        ProfileHorizontalPager()
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
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .then(modifier),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            CoverImage()
            Column(
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 64.dp, end = 24.dp),
            ) {
                ChatIconButton(
                    modifier = Modifier.padding(bottom = 20.dp),
                    isBadgeEnabled = true,
                )
                BookmarkIconButton(
                    modifier = Modifier.padding(bottom = 24.dp),
                )
            }
        }
    }
}

@Composable
private fun BoxScope.CoverImage() {
    Image(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(375.dp),
        painter = painterResource(id = R.drawable.demo_my_profile_cover),
        contentDescription = "Cover image",
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun ProfileTabView() {
}

@Composable
private fun ProfileHorizontalPager() {
}

@Preview(showSystemUi = true)
@Composable
private fun ProfileContentPreview() {
    ProfileContent(
        state = ProfileState.EMPTY,
        onAction = {},
    )
}
