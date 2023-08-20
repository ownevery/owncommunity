package com.gasparaitis.owncommunity.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasparaitis.owncommunity.domain.search.usecase.SearchUseCase
import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
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
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.EMPTY)
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private val _navEvent = MutableSharedFlow<SearchNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    init {
        onCreated()
    }

    private fun onCreated() {
        _state.value = searchUseCase.getState()
    }

    fun onAction(action: SearchAction) {
        when (action) {
            SearchAction.OnSearchBarClick -> onSearchBarClick()
            is SearchAction.OnSearchBarQueryChange -> onSearchBarQueryChange(action.query)
            SearchAction.OnSearchIconRepeatClick -> onSearchIconRepeatClick()
            is SearchAction.OnTabSelected -> onTabSelected(action.index)
            is SearchAction.OnPostAction -> {
                when (action.postAction) {
                    is PostAction.OnAuthorClick -> onPostAuthorClick(action.postAction.item)
                    is PostAction.OnBodyClick -> onPostBodyClick()
                    is PostAction.OnBookmarkClick -> onPostBookmarkClick(action.postAction.item)
                    is PostAction.OnCommentClick -> onPostCommentClick(action.postAction.item)
                    is PostAction.OnLikeClick -> onPostLikeClick(action.postAction.item)
                    is PostAction.OnShareClick -> onPostShareClick(action.postAction.item)
                }
            }
            is SearchAction.OnProfileBodyClick -> onProfileBodyClick(action.profile)
            is SearchAction.OnProfileFollowButtonClick -> onProfileFollowButtonClick(action.profile)
        }
    }

    private fun onProfileFollowButtonClick(profile: Profile) {
        _state.update { state ->
            state.copy(
                profiles = state.profiles.map {
                    if (profile.id == it.id) {
                        profile.copy(isFollowed = profile.isFollowed.not())
                    } else {
                        it
                    }
                }.toMutableList()
            )
        }
    }

    private fun onProfileBodyClick(profile: Profile) {
        viewModelScope.launch {
            _navEvent.emit(SearchNavEvent.OpenProfile)
        }
    }

    private fun onSearchBarClick() { /* Opens search action bottom sheet. */ }

    private fun onSearchBarQueryChange(text: String) {
        _state.update { it.copy(searchText = it.searchText.copy(text = text)) }
    }

    private fun onSearchIconRepeatClick() {
        viewModelScope.launch {
            _navEvent.emit(SearchNavEvent.ScrollUp)
        }
    }

    private fun onTabSelected(index: Int) {
        _state.update { it.copy(selectedTabIndex = index) }
    }

    private fun onPostBodyClick() {
        viewModelScope.launch {
            _navEvent.emit(SearchNavEvent.OpenPost)
        }
    }

    private fun onPostAuthorClick(item: Post) {
        viewModelScope.launch {
            _navEvent.emit(SearchNavEvent.OpenPostAuthorProfile)
        }
    }

    private fun onPostLikeClick(post: Post) =
        updatePostsByItemId(
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
            _navEvent.emit(SearchNavEvent.OpenPost)
        }
    }

    private fun onPostShareClick(post: Post) =
        updatePostsByItemId(
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
        updatePostsByItemId(
            post = post.copy(
                isBookmarked = post.isBookmarked.not(),
            ),
        )

    private fun updatePostsByItemId(post: Post) {
        when (state.value.selectedTabIndex) {
            0 -> {
                _state.value = _state.value.copy(
                    trendingPosts = _state.value.trendingPosts.map { item ->
                        if (item.id == post.id) post else item
                    },
                )
            }
            1 -> {
                _state.value = _state.value.copy(
                    latestPosts = _state.value.latestPosts.map { item ->
                        if (item.id == post.id) post else item
                    },
                )
            }
        }
    }
}
