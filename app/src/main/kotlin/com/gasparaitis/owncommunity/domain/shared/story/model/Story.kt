package com.gasparaitis.owncommunity.domain.shared.story.model

import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile

data class Story(
    val id: String,
    val storyImages: List<Int>,
    val profile: Profile,
    val isRead: Boolean,
    val index: Int,
) {
    companion object {
        val EMPTY = Story(
            id = "",
            storyImages = listOf(),
            profile = Profile.EMPTY,
            isRead = false,
            index = 0,
        )
    }
}
