package com.gasparaitis.owncommunity.domain.shared.story.model

import androidx.annotation.DrawableRes

data class Story(
    val id: String,
    val storyImages: List<Int>,
    @DrawableRes val profileImage: Int,
    val isRead: Boolean,
    val index: Int,
) {
    companion object {
        val EMPTY = Story(
            id = "",
            storyImages = listOf(),
            profileImage = 0,
            isRead = false,
            index = 0,
        )
    }
}
