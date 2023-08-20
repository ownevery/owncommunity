package com.gasparaitis.owncommunity.domain.shared.story.model

import androidx.annotation.DrawableRes

data class Story(
    val id: String,
    @DrawableRes val storyImage: Int,
    @DrawableRes val profileImage: Int,
    val isRead: Boolean,
) {
    companion object {
        val EMPTY = Story(
            id = "",
            storyImage = 0,
            profileImage = 0,
            isRead = false,
        )
    }
}
