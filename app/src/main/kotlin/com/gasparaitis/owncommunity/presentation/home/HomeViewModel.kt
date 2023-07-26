package com.gasparaitis.owncommunity.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasparaitis.owncommunity.domain.home.model.HomePost
import com.gasparaitis.owncommunity.domain.home.model.HomeStory
import com.gasparaitis.owncommunity.domain.home.usecase.HomeStateDemo
import com.gasparaitis.owncommunity.domain.home.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.EMPTY)
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _navEvent = MutableSharedFlow<HomeNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    init {
        onCreated()
    }

    private fun onCreated() {
        _state.value = _state.value.copy(
            posts = HomeStateDemo.posts,
            stories = HomeStateDemo.stories,
        )
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnPostBookmarkClick -> onPostBookmarkClick(action.item)
            is HomeAction.OnPostCommentClick -> onPostCommentClick(action.item)
            is HomeAction.OnPostLikeClick -> onPostLikeClick(action.item)
            is HomeAction.OnPostShareClick -> onPostShareClick(action.item)
            is HomeAction.OnStoryClick -> onStoryClick(action.item)
            HomeAction.OnAlertIconClick -> onAlertIconClick()
            is HomeAction.OnPostAuthorClick -> onPostAuthorClick(action.item)
            is HomeAction.OnPostBodyClick -> onPostBodyClick()
            HomeAction.OnHomeIconRepeatClick -> onHomeIconRepeatClick()
        }
    }

    private fun onHomeIconRepeatClick() {
        viewModelScope.launch {
            _navEvent.emit(HomeNavEvent.ScrollUp)
        }
    }

    private fun onPostBodyClick() {
        viewModelScope.launch {
            _navEvent.emit(HomeNavEvent.OpenPost)
        }
    }

    private fun onPostAuthorClick(item: HomePost) {
        viewModelScope.launch {
            _navEvent.emit(HomeNavEvent.OpenPostAuthorProfile)
        }
    }

    private fun onAlertIconClick() {
        viewModelScope.launch {
            _navEvent.emit(HomeNavEvent.OpenAlerts)
        }
    }

    private fun onStoryClick(story: HomeStory) {
        viewModelScope.launch {
            _navEvent.emit(HomeNavEvent.OpenStory)
            _state.value = _state.value.copy(
                stories = _state.value.stories.map { storyItem ->
                    if (storyItem.id == story.id) storyItem.copy(isRead = true) else storyItem
                },
            )
        }
    }

    private fun onPostLikeClick(homePost: HomePost) =
        updateStateByItemId(
            homePost = homePost.copy(
                isLiked = homePost.isLiked.not(),
                likeCount = if (homePost.isLiked) {
                    homePost.likeCount.dec()
                } else {
                    homePost.likeCount.inc()
                },
            ),
        )

    private fun onPostCommentClick(homePost: HomePost) {
        viewModelScope.launch {
            _navEvent.emit(HomeNavEvent.OpenPost)
        }
    }

    private fun onPostShareClick(homePost: HomePost) =
        updateStateByItemId(
            homePost = homePost.copy(
                isShared = homePost.isShared.not(),
                shareCount = if (homePost.isShared) {
                    homePost.shareCount.dec()
                } else {
                    homePost.shareCount.inc()
                },
            ),
        )

    private fun onPostBookmarkClick(homePost: HomePost) =
        updateStateByItemId(
            homePost = homePost.copy(
                isBookmarked = homePost.isBookmarked.not(),
            ),
        )

    private fun updateStateByItemId(homePost: HomePost) {
        _state.value = _state.value.copy(
            posts = _state.value.posts.map { item ->
                if (item.id == homePost.id) homePost else item
            },
        )
    }
}
