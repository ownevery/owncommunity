package com.gasparaitis.owncommunity.presentation.home

import androidx.compose.runtime.Stable
import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.domain.shared.story.model.Story
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class HomeState(
    val areAllAlertsRead: Boolean,
    val posts: PersistentList<Post>,
    val stories: PersistentList<Story>,
    val event: Event?,
) {
    companion object {
        val EMPTY =
            HomeState(
                areAllAlertsRead = false,
                posts = persistentListOf(),
                stories = persistentListOf(),
                event = null,
            )
    }

    sealed interface Event

    data object OnAlertIconClick : Event

    data class OnStoryClick(val item: Story) : Event

    data class OnPostEvent(val postEvent: Post.Event) : Event

    data object NavigateToChatListScreen : Event

    data object NavigateToStoryListScreen : Event

    data object NavigateToPostAuthorProfileScreen : Event

    data object NavigateToPostScreen : Event
}
