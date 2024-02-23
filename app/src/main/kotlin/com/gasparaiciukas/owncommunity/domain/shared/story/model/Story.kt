package com.gasparaiciukas.owncommunity.domain.shared.story.model

import com.gasparaiciukas.owncommunity.domain.shared.profile.model.Profile
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class Story(
    val id: String,
    val profile: Profile,
    val isRead: Boolean,
    val entryIndex: Int,
    val entries: PersistentList<StoryEntry>,
) {
    companion object {
        val EMPTY =
            Story(
                id = "",
                profile = Profile.EMPTY,
                isRead = false,
                entryIndex = 0,
                entries = persistentListOf(),
            )
    }
}
