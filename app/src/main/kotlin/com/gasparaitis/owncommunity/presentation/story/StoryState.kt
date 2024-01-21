package com.gasparaitis.owncommunity.presentation.story

import androidx.compose.ui.text.input.TextFieldValue
import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
import com.gasparaitis.owncommunity.domain.shared.story.model.Story
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class StoryState(
    val selectedTabIndex: Int,
    val searchText: TextFieldValue,
    val stories: PersistentList<Story>,
    val event: Event?,
) {
    companion object {
        val EMPTY =
            StoryState(
                selectedTabIndex = 0,
                searchText = TextFieldValue(),
                stories = persistentListOf(),
                event = null,
            )
    }

    sealed interface Event

    data object OnStoryReplyBarClick : Event

    data object OnStoryReplySend : Event

    data class OnStoryReplyQueryChange(val query: String) : Event

    data class OnTabSelected(val index: Int) : Event

    data class OnProfileClick(val profile: Profile) : Event

    data class OnStoryGoBack(val storyIndex: Int, val storyItemIndex: Int) : Event

    data class OnStoryGoForward(val storyIndex: Int, val storyItemIndex: Int) : Event

    data object NavigateToProfileScreen : Event
}
