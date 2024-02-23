package com.gasparaiciukas.owncommunity.presentation.home

import androidx.lifecycle.ViewModel
import com.gasparaiciukas.owncommunity.domain.alerts.usecase.AlertListUseCase
import com.gasparaiciukas.owncommunity.domain.shared.post.model.Post
import com.gasparaiciukas.owncommunity.domain.shared.post.usecase.PostUseCase
import com.gasparaiciukas.owncommunity.domain.shared.story.model.Story
import com.gasparaiciukas.owncommunity.domain.shared.story.usecase.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alertListUseCase: AlertListUseCase,
    private val postUseCase: PostUseCase,
    private val storyUseCase: StoryUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.EMPTY)
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        onCreated()
    }

    private fun onCreated() {
        val areAllAlertsRead = alertListUseCase.getAreAllItemsRead()
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

    fun onEvent(event: HomeState.Event) {
        when (event) {
            is HomeState.OnStoryClick -> onStoryClick(event.item)
            HomeState.OnAlertIconClick -> onAlertIconClick()
            is HomeState.OnPostEvent -> {
                when (event.postEvent) {
                    is Post.OnAuthorClick -> onPostAuthorClick(event.postEvent.item)
                    is Post.OnBodyClick -> onPostBodyClick()
                    is Post.OnBookmarkClick -> onPostBookmarkClick(event.postEvent.item)
                    is Post.OnCommentClick -> onPostCommentClick(event.postEvent.item)
                    is Post.OnLikeClick -> onPostLikeClick(event.postEvent.item)
                    is Post.OnShareClick -> onPostShareClick(event.postEvent.item)
                }
            }
            HomeState.NavigateToChatListScreen,
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
                event = HomeState.NavigateToChatListScreen,
            )
        }
    }

    private fun onStoryClick(story: Story) {
        _uiState.update { state ->
            val stories =
                state.stories.updateStory(
                    story.copy(
                        isRead = true,
                    ),
                )
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

    private fun onPostLikeClick(post: Post) {
        _uiState.update { state ->
            val isLiked = post.isLiked.not()
            val likeCount =
                if (post.isLiked) {
                    post.likeCount.dec()
                } else {
                    post.likeCount.inc()
                }
            val posts =
                state.posts.updatePost(
                    post.copy(
                        isLiked = isLiked,
                        likeCount = likeCount,
                    ),
                )
            state.copy(
                posts = posts,
            )
        }
    }

    private fun onPostCommentClick(post: Post) {
        _uiState.update { state ->
            state.copy(
                event = HomeState.NavigateToPostScreen,
            )
        }
    }

    private fun onPostShareClick(post: Post) {
        _uiState.update { state ->
            val isShared = post.isShared.not()
            val shareCount =
                if (post.isShared) {
                    post.shareCount.dec()
                } else {
                    post.shareCount.inc()
                }
            val posts =
                state.posts.updatePost(
                    post.copy(
                        isShared = isShared,
                        shareCount = shareCount,
                    ),
                )
            state.copy(
                posts = posts,
            )
        }
    }

    private fun onPostBookmarkClick(post: Post) {
        _uiState.update { state ->
            val posts =
                state.posts.updatePost(
                    post.copy(
                        isBookmarked = post.isBookmarked.not(),
                    ),
                )
            state.copy(
                posts = posts,
            )
        }
    }

    private fun PersistentList<Post>.updatePost(post: Post): PersistentList<Post> =
        mutate { list ->
            val index = list.indexOfFirst { item -> item.id == post.id }
            if (index != -1) {
                list[index] = post
            }
        }

    private fun PersistentList<Story>.updateStory(story: Story): PersistentList<Story> =
        mutate { list ->
            val index = list.indexOfFirst { item -> item.id == story.id }
            if (index != -1) {
                list[index] = story
            }
        }
}
