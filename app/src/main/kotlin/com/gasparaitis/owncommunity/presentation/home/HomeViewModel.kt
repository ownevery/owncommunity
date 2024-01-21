package com.gasparaitis.owncommunity.presentation.home

import androidx.lifecycle.ViewModel
import com.gasparaitis.owncommunity.domain.alerts.usecase.AlertsUseCase
import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.domain.shared.post.usecase.PostUseCase
import com.gasparaitis.owncommunity.domain.shared.story.model.Story
import com.gasparaitis.owncommunity.domain.shared.story.usecase.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alertsUseCase: AlertsUseCase,
    private val postUseCase: PostUseCase,
    private val storyUseCase: StoryUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.EMPTY)
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        onCreated()
    }

    private fun onCreated() {
        val areAllAlertsRead = alertsUseCase.getAreAllItemsRead()
        val posts = postUseCase.getHomePosts()
        val stories = storyUseCase.getStories()
        _uiState.update { state ->
            state.copy(
                areAllAlertsRead = areAllAlertsRead,
                posts = posts,
                stories = stories,
            )
        }
    }

    fun onEvent(action: HomeState.Event) {
        when (action) {
            is HomeState.OnStoryClick -> onStoryClick(action.item)
            HomeState.OnAlertIconClick -> onAlertIconClick()
            is HomeState.OnPostEvent -> {
                when (action.postEvent) {
                    is Post.OnAuthorClick -> onPostAuthorClick(action.postEvent.item)
                    is Post.OnBodyClick -> onPostBodyClick()
                    is Post.OnBookmarkClick -> onPostBookmarkClick(action.postEvent.item)
                    is Post.OnCommentClick -> onPostCommentClick(action.postEvent.item)
                    is Post.OnLikeClick -> onPostLikeClick(action.postEvent.item)
                    is Post.OnShareClick -> onPostShareClick(action.postEvent.item)
                }
            }
            HomeState.NavigateToAlertListScreen,
            HomeState.NavigateToPostAuthorProfileScreen,
            HomeState.NavigateToPostScreen,
            HomeState.NavigateToStoryListScreen -> {}
        }
    }

    fun onEventHandled(event: HomeState.Event?) {
        _uiState.update { state ->
            if (state.event == event) {
                state.copy(
                    event = null,
                )
            } else {
                state
            }
        }
    }

    private fun onAlertIconClick() {
        _uiState.update { state ->
            state.copy(
                event = HomeState.NavigateToAlertListScreen,
            )
        }
    }

    private fun onStoryClick(story: Story) {
        _uiState.update { state ->
            val stories =
                state.stories.mutate { storyList ->
                    storyList.map { storyItem ->
                        if (storyItem.id == story.id) {
                            storyItem.copy(isRead = true)
                        } else {
                            storyItem
                        }
                    }
                }
            state.copy(
                event = HomeState.NavigateToStoryListScreen,
                stories = stories,
            )
        }
    }

    private fun onPostBodyClick() {
        _uiState.update { state ->
            state.copy(
                event = HomeState.NavigateToPostScreen,
            )
        }
    }

    private fun onPostAuthorClick(item: Post) {
        _uiState.update { state ->
            state.copy(
                event = HomeState.NavigateToPostAuthorProfileScreen,
            )
        }
    }

    private fun onPostLikeClick(post: Post) =
        updateStateByItemId(
            post =
                post.copy(
                    isLiked = post.isLiked.not(),
                    likeCount =
                        if (post.isLiked) {
                            post.likeCount.dec()
                        } else {
                            post.likeCount.inc()
                        },
                ),
        )

    private fun onPostCommentClick(post: Post) {
        _uiState.update { state ->
            state.copy(
                event = HomeState.NavigateToPostScreen,
            )
        }
    }

    private fun onPostShareClick(post: Post) =
        updateStateByItemId(
            post =
                post.copy(
                    isShared = post.isShared.not(),
                    shareCount =
                        if (post.isShared) {
                            post.shareCount.dec()
                        } else {
                            post.shareCount.inc()
                        },
                ),
        )

    private fun onPostBookmarkClick(post: Post) =
        updateStateByItemId(
            post =
                post.copy(
                    isBookmarked = post.isBookmarked.not(),
                ),
        )

    private fun updateStateByItemId(post: Post) {
        _uiState.update { state ->
            val posts =
                state.posts.mutate { postList ->
                    postList.map { postItem ->
                        if (postItem.id == post.id) {
                            post
                        } else {
                            postItem
                        }
                    }
                }
            state.copy(
                posts = posts,
            )
        }
    }
}
