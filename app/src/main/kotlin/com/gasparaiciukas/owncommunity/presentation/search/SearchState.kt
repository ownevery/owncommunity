package com.gasparaiciukas.owncommunity.presentation.search

import androidx.compose.ui.text.input.TextFieldValue
import com.gasparaiciukas.owncommunity.domain.shared.post.model.Post
import com.gasparaiciukas.owncommunity.domain.shared.profile.model.Profile
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class SearchState(
    val selectedTabIndex: Int,
    val searchText: TextFieldValue,
    val trendingPosts: PersistentList<Post>,
    val latestPosts: PersistentList<Post>,
    val profiles: PersistentList<Profile>,
    val event: Event?,
) {
    companion object {
        val EMPTY =
            SearchState(
                selectedTabIndex = 0,
                searchText = TextFieldValue(),
                trendingPosts = persistentListOf(),
                latestPosts = persistentListOf(),
                profiles = persistentListOf(),
                event = null,
            )
    }

    sealed interface Event

    data object OnSearchIconRepeatClick : Event

    data object OnSearchBarClick : Event

    data class OnSearchBarQueryChange(val query: String) : Event

    data class OnTabSelected(val index: Int) : Event

    data class OnPostEvent(val postEvent: Post.Event) : Event

    data class OnProfileBodyClick(val profile: Profile) : Event

    data class OnProfileFollowButtonClick(val profile: Profile) : Event

    data object NavigateToPostAuthorProfileScreen : Event

    data object NavigateToProfileScreen : Event

    data object NavigateToPostScreen : Event

    data object ScrollUp : Event
}
