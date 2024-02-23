package com.gasparaiciukas.owncommunity.presentation.story

import androidx.compose.ui.text.input.TextFieldValue
import com.gasparaiciukas.owncommunity.domain.shared.profile.model.Profile
import com.gasparaiciukas.owncommunity.domain.shared.story.model.Story
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class StoryState(
    val currentPage: Int,
    val searchText: TextFieldValue,
    val stories: PersistentList<Story>,
    val event: Event?,
) {
    companion object {
        val EMPTY =
            StoryState(
                currentPage = 0,
                searchText = TextFieldValue(),
                stories = persistentListOf(),
                event = null,
            )
    }

    sealed interface Event

    data object OnStoryReplyBarClick : Event

    data object OnStoryReplySend : Event

    data class OnStoryReplyQueryChange(val query: String) : Event

    data class OnPageSelected(val index: Int) : Event

    data class OnProfileClick(val profile: Profile) : Event

    data class OnStoryGoBack(
        val storyIndex: Int,
        val scrollToPage: suspend (Int) -> Unit,
    ) : Event

    data class OnStoryGoForward(
        val storyIndex: Int,
        val scrollToPage: suspend (Int) -> Unit,
    ) : Event

    data object NavigateToProfileScreen : Event
}
