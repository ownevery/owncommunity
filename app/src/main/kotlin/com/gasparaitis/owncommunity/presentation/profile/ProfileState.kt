package com.gasparaitis.owncommunity.presentation.profile

import com.gasparaitis.owncommunity.domain.shared.comment.Comment
import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ProfileState(
    val event: Event?,
    val latestPosts: PersistentList<Post>,
    val likedPosts: PersistentList<Post>,
    val popularPosts: PersistentList<Post>,
    val profile: Profile,
    val replies: PersistentList<Comment>,
) {
    companion object {
        val EMPTY =
            ProfileState(
                profile = Profile.EMPTY,
                event = null,
                latestPosts = persistentListOf(),
                likedPosts = persistentListOf(),
                popularPosts = persistentListOf(),
                replies = persistentListOf(),
            )
    }

    sealed interface Event

    data object OnChatButtonClick : Event

    data object OnBookmarkButtonClick : Event

    data object OnEditProfileButtonClick : Event

    data class OnTabSelected(val index: Int) : Event

    data class OnPostEvent(val postEvent: Post.Event) : Event

    data object NavigateToChatListScreen : Event

    data object NavigateToBookmarksScreen : Event

    data object NavigateToEditProfileScreen : Event

    data object NavigateToPostAuthorProfileScreen : Event

    data object NavigateToPostScreen : Event
}
