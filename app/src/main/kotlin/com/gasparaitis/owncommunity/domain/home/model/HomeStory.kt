package com.gasparaitis.owncommunity.domain.home.model

import androidx.annotation.DrawableRes

data class HomeStory(
    val id: String,
    @DrawableRes val storyImage: Int,
    @DrawableRes val profileImage: Int,
    val isRead: Boolean,
) {
    companion object {
        val EMPTY = HomeStory(
            id = "",
            storyImage = 0,
            profileImage = 0,
            isRead = false,
        )
    }
}
