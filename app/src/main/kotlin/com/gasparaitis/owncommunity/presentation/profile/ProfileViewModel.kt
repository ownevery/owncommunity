package com.gasparaitis.owncommunity.presentation.profile

import androidx.lifecycle.ViewModel
import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.domain.shared.post.usecase.PostUseCase
import com.gasparaitis.owncommunity.domain.shared.profile.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val postUseCase: PostUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.EMPTY)
    val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()

    init {
        onCreated()
    }

    private fun onCreated() {
        val profile = profileUseCase.getMyProfile()
        val latestPosts = postUseCase.getLatestPosts()
        val likedPosts = postUseCase.getHomePosts()
        val popularPosts = postUseCase.getTrendingPosts()
        _uiState.update { state ->
            state.copy(
                profile = profile,
                latestPosts = latestPosts,
                likedPosts = likedPosts,
                popularPosts = popularPosts,
            )
        }
    }

    fun onEvent(event: ProfileState.Event) {
        when (event) {
            ProfileState.OnBookmarkButtonClick -> onBookmarkButtonClick()
            ProfileState.OnChatButtonClick -> onChatButtonClick()
            ProfileState.OnEditProfileButtonClick -> onEditProfileButtonClick()
            is ProfileState.OnPostEvent -> {
                when (event.postEvent) {
                    is Post.OnAuthorClick -> onPostAuthorClick(event.postEvent.item)
                    is Post.OnBodyClick -> onPostBodyClick()
                    is Post.OnBookmarkClick -> onPostBookmarkClick(event.postEvent.item)
                    is Post.OnCommentClick -> onPostCommentClick(event.postEvent.item)
                    is Post.OnLikeClick -> onPostLikeClick(event.postEvent.item)
                    is Post.OnShareClick -> onPostShareClick(event.postEvent.item)
                }
            }
            is ProfileState.OnTabSelected -> onTabSelected(event.index)
            ProfileState.NavigateToBookmarksScreen -> {}
            ProfileState.NavigateToChatListScreen -> {}
            ProfileState.NavigateToEditProfileScreen -> {}
            ProfileState.NavigateToPostAuthorProfileScreen -> {}
            ProfileState.NavigateToPostScreen -> {}
        }
    }

    fun onEventHandled(event: ProfileState.Event?) {
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

    private fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }

    private fun onEditProfileButtonClick() {
    }

    private fun onChatButtonClick() {
    }

    private fun onBookmarkButtonClick() {
        _uiState.update { state ->
            state.copy(
                event = ProfileState.NavigateToBookmarksScreen,
            )
        }
    }

    private fun onPostBodyClick() {
        _uiState.update { state ->
            state.copy(
                event = ProfileState.NavigateToPostScreen,
            )
        }
    }

    private fun onPostAuthorClick(item: Post) {
        _uiState.update { state ->
            state.copy(
                event = ProfileState.NavigateToPostAuthorProfileScreen,
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
                state.latestPosts.updatePost(
                    post.copy(
                        isLiked = isLiked,
                        likeCount = likeCount,
                    ),
                )
            state.copy(
                latestPosts = posts,
            )
        }
    }

    private fun onPostCommentClick(post: Post) {
        _uiState.update { state ->
            state.copy(
                event = ProfileState.NavigateToPostScreen,
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
                state.latestPosts.updatePost(
                    post.copy(
                        isShared = isShared,
                        shareCount = shareCount,
                    ),
                )
            state.copy(
                latestPosts = posts,
            )
        }
    }

    private fun onPostBookmarkClick(post: Post) {
        _uiState.update { state ->
            val posts =
                state.latestPosts.updatePost(
                    post.copy(
                        isBookmarked = post.isBookmarked.not(),
                    ),
                )
            state.copy(
                latestPosts = posts,
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
}
