package com.gasparaitis.owncommunity.domain.shared.story.model

import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class Story(
    val id: String,
    val profile: Profile,
    val isRead: Boolean,
    val index: Int,
    val storyEntries: PersistentList<StoryEntry>,
) {
    companion object {
        val EMPTY =
            Story(
                id = "",
                profile = Profile.EMPTY,
                isRead = false,
                index = 0,
                storyEntries = persistentListOf(),
            )
    }
}
