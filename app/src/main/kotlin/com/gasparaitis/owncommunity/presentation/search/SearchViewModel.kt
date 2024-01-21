package com.gasparaitis.owncommunity.presentation.search

import androidx.lifecycle.ViewModel
import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.domain.shared.post.usecase.PostUseCase
import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
import com.gasparaitis.owncommunity.domain.shared.profile.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val postUseCase: PostUseCase,
    private val profileUseCase: ProfileUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.EMPTY)
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    init {
        onCreated()
    }

    private fun onCreated() {
        val trendingPosts = postUseCase.getTrendingPosts()
        val latestPosts = postUseCase.getLatestPosts()
        val profiles = profileUseCase.getSuggestedProfiles()
        _uiState.update { state ->
            state.copy(
                selectedTabIndex = state.selectedTabIndex,
                searchText = state.searchText,
                trendingPosts = trendingPosts,
                latestPosts = latestPosts,
                profiles = profiles,
            )
        }
    }

    fun onEvent(event: SearchState.Event) {
        when (event) {
            SearchState.OnSearchBarClick -> onSearchBarClick()
            is SearchState.OnSearchBarQueryChange -> onSearchBarQueryChange(event.query)
            SearchState.OnSearchIconRepeatClick -> onSearchIconRepeatClick()
            is SearchState.OnTabSelected -> onTabSelected(event.index)
            is SearchState.OnPostEvent -> {
                when (event.postEvent) {
                    is Post.OnAuthorClick -> onPostAuthorClick(event.postEvent.item)
                    is Post.OnBodyClick -> onPostBodyClick()
                    is Post.OnBookmarkClick -> onPostBookmarkClick(event.postEvent.item)
                    is Post.OnCommentClick -> onPostCommentClick(event.postEvent.item)
                    is Post.OnLikeClick -> onPostLikeClick(event.postEvent.item)
                    is Post.OnShareClick -> onPostShareClick(event.postEvent.item)
                }
            }
            is SearchState.OnProfileBodyClick -> onProfileBodyClick(event.profile)
            is SearchState.OnProfileFollowButtonClick -> onProfileFollowButtonClick(event.profile)
            SearchState.NavigateToPostAuthorProfileScreen -> {}
            SearchState.NavigateToPostScreen -> {}
            SearchState.NavigateToProfileScreen -> {}
            SearchState.ScrollUp -> {}
        }
    }

    fun onEventHandled(event: SearchState.Event?) {
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

    private fun onProfileFollowButtonClick(profile: Profile) {
        _uiState.update { state ->
            val profiles =
                state.profiles.mutate { profileList ->
                    profileList.map { p ->
                        if (profile.id == p.id) {
                            profile.copy(isFollowed = profile.isFollowed.not())
                        } else {
                            p
                        }
                    }
                }
            state.copy(
                profiles = profiles,
            )
        }
    }

    private fun onProfileBodyClick(profile: Profile) {
        _uiState.update { state ->
            state.copy(
                event = SearchState.NavigateToProfileScreen,
            )
        }
    }

    private fun onSearchBarClick() { /* Opens search action bottom sheet. */ }

    private fun onSearchBarQueryChange(text: String) {
        _uiState.update { it.copy(searchText = it.searchText.copy(text = text)) }
    }

    private fun onSearchIconRepeatClick() {
        _uiState.update { state ->
            state.copy(
                event = SearchState.ScrollUp,
            )
        }
    }

    private fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }

    private fun onPostBodyClick() {
        _uiState.update { state ->
            state.copy(
                event = SearchState.NavigateToPostScreen,
            )
        }
    }

    private fun onPostAuthorClick(item: Post) {
        _uiState.update { state ->
            state.copy(
                event = SearchState.NavigateToPostAuthorProfileScreen,
            )
        }
    }

    private fun onPostLikeClick(post: Post) =
        updatePostsByItemId(
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
                event = SearchState.NavigateToPostScreen,
            )
        }
    }

    private fun onPostShareClick(post: Post) =
        updatePostsByItemId(
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
        updatePostsByItemId(
            post =
                post.copy(
                    isBookmarked = post.isBookmarked.not(),
                ),
        )

    private fun updatePostsByItemId(post: Post) {
        _uiState.update { state ->
            when (state.selectedTabIndex) {
                0 -> {
                    val trendingPosts =
                        state.trendingPosts.mutate { postList ->
                            postList.map { item ->
                                if (item.id == post.id) post else item
                            }
                        }
                    state.copy(
                        trendingPosts = trendingPosts,
                    )
                }
                1 -> {
                    val latestPosts =
                        state.latestPosts.mutate { postList ->
                            postList.map { item ->
                                if (item.id == post.id) post else item
                            }
                        }
                    state.copy(
                        latestPosts = latestPosts,
                    )
                }
                else -> {
                    state
                }
            }
        }
    }
}
