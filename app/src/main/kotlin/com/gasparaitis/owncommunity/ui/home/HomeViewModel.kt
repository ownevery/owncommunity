package com.gasparaitis.owncommunity.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnPostBookmarkClick -> onPostBookmarkClick(action.item)
            is HomeAction.OnPostCommentClick -> onPostCommentClick(action.item)
            is HomeAction.OnPostLikeClick -> onPostLikeClick(action.item)
            is HomeAction.OnPostShareClick -> onPostShareClick(action.item)
            is HomeAction.OnStoryClick -> onStoryClick(action.story)
        }
    }

    private fun onStoryClick(story: HomeStory) {
        _state.value = _state.value.copy(
            stories = _state.value.stories.map { storyItem ->
                if (storyItem.id == story.id) storyItem.copy(isRead = true) else storyItem
            },
        )
    }

    private fun onPostLikeClick(homeItem: HomeItem) =
        updateStateByItemId(
            homeItem = homeItem.copy(
                isLiked = homeItem.isLiked.not(),
                likeCount = if (homeItem.isLiked) {
                    homeItem.likeCount.dec()
                } else {
                    homeItem.likeCount.inc()
                },
            ),
        )

    private fun onPostCommentClick(homeItem: HomeItem) {
        // Do nothing.
    }

    private fun onPostShareClick(homeItem: HomeItem) =
        updateStateByItemId(
            homeItem = homeItem.copy(
                isShared = homeItem.isShared.not(),
                shareCount = if (homeItem.isShared) {
                    homeItem.shareCount.dec()
                } else {
                    homeItem.shareCount.inc()
                },
            ),
        )

    private fun onPostBookmarkClick(homeItem: HomeItem) =
        updateStateByItemId(
            homeItem = homeItem.copy(
                isBookmarked = homeItem.isBookmarked.not(),
            ),
        )

    private fun updateStateByItemId(homeItem: HomeItem) {
        _state.value = _state.value.copy(
            posts = _state.value.posts.map { item ->
                if (item.id == homeItem.id) homeItem else item
            },
        )
    }
}
