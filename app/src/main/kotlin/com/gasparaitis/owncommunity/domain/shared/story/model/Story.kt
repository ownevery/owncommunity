package com.gasparaitis.owncommunity.domain.shared.story.model

import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile

data class Story(
    val id: String,
    val profile: Profile,
    val isRead: Boolean,
    val index: Int,
    val storyEntries: List<StoryEntry>,
) {
    companion object {
        val EMPTY = Story(
            id = "",
            profile = Profile.EMPTY,
            isRead = false,
            index = 0,
            storyEntries = listOf(),
        )
    }
}
