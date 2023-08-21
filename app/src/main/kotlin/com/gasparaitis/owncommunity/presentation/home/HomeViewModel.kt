package com.gasparaitis.owncommunity.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasparaitis.owncommunity.domain.alerts.usecase.AlertsUseCase
import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.domain.shared.post.usecase.PostUseCase
import com.gasparaitis.owncommunity.domain.shared.story.model.Story
import com.gasparaitis.owncommunity.domain.shared.story.usecase.StoryUseCase
import com.gasparaitis.owncommunity.presentation.shared.composables.post.PostAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alertsUseCase: AlertsUseCase,
    private val postUseCase: PostUseCase,
    private val storyUseCase: StoryUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.EMPTY)
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _navEvent = MutableSharedFlow<HomeNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    init {
        onCreated()
    }

    private fun onCreated() {
        val areAllAlertsRead = alertsUseCase.getAreAllItemsRead()
        val posts = postUseCase.getHomePosts()
        val stories = storyUseCase.getStories()
        _state.update { state ->
            state.copy(
                areAllAlertsRead = areAllAlertsRead,
                posts = posts,
                stories = stories,
            )
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnStoryClick -> onStoryClick(action.item)
            HomeAction.OnAlertIconClick -> onAlertIconClick()
            is HomeAction.OnPostAction -> {
                when (action.postAction) {
                    is PostAction.OnAuthorClick -> onPostAuthorClick(action.postAction.item)
                    is PostAction.OnBodyClick -> onPostBodyClick()
                    is PostAction.OnBookmarkClick -> onPostBookmarkClick(action.postAction.item)
                    is PostAction.OnCommentClick -> onPostCommentClick(action.postAction.item)
                    is PostAction.OnLikeClick -> onPostLikeClick(action.postAction.item)
                    is PostAction.OnShareClick -> onPostShareClick(action.postAction.item)
                }
            }
        }
    }

    private fun onHomeIconRepeatClick() {
        viewModelScope.launch {
            _navEvent.emit(HomeNavEvent.ScrollUp)
        }
    }

    private fun onAlertIconClick() {
        viewModelScope.launch {
            _navEvent.emit(HomeNavEvent.OpenAlerts)
        }
    }

    private fun onStoryClick(story: Story) {
        viewModelScope.launch {
            _navEvent.emit(HomeNavEvent.OpenStory)
            _state.value = _state.value.copy(
                stories = _state.value.stories.map { storyItem ->
                    if (storyItem.id == story.id) storyItem.copy(isRead = true) else storyItem
                },
            )
        }
    }

    private fun onPostBodyClick() {
        viewModelScope.launch {
            _navEvent.emit(HomeNavEvent.OpenPost)
        }
    }

    private fun onPostAuthorClick(item: Post) {
        viewModelScope.launch {
            _navEvent.emit(HomeNavEvent.OpenPostAuthorProfile)
        }
    }

    private fun onPostLikeClick(post: Post) =
        updateStateByItemId(
            post = post.copy(
                isLiked = post.isLiked.not(),
                likeCount = if (post.isLiked) {
                    post.likeCount.dec()
                } else {
                    post.likeCount.inc()
                },
            ),
        )

    private fun onPostCommentClick(post: Post) {
        viewModelScope.launch {
            _navEvent.emit(HomeNavEvent.OpenPost)
        }
    }

    private fun onPostShareClick(post: Post) =
        updateStateByItemId(
            post = post.copy(
                isShared = post.isShared.not(),
                shareCount = if (post.isShared) {
                    post.shareCount.dec()
                } else {
                    post.shareCount.inc()
                },
            ),
        )

    private fun onPostBookmarkClick(post: Post) =
        updateStateByItemId(
            post = post.copy(
                isBookmarked = post.isBookmarked.not(),
            ),
        )

    private fun updateStateByItemId(post: Post) {
        _state.value = _state.value.copy(
            posts = _state.value.posts.map { item ->
                if (item.id == post.id) post else item
            },
        )
    }
}
