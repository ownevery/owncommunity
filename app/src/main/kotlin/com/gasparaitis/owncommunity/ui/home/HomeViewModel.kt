package com.gasparaitis.owncommunity.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    fun onStoryClick(story: HomeStory) {
        _state.value = _state.value.copy(
            stories = _state.value.stories.map { storyItem ->
                if (storyItem.id == story.id) storyItem.copy(isRead = true) else storyItem
            },
        )
    }
    fun onItemActionClick(homeItem: HomeItem) {
        _state.value = _state.value.copy(
            posts = _state.value.posts.map { postItem ->
                if (postItem.id == homeItem.id) homeItem else postItem
            },
        )
    }
}
