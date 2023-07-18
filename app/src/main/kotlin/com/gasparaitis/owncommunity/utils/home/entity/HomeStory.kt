package com.gasparaitis.owncommunity.utils.home.entity

import androidx.annotation.DrawableRes
import com.gasparaitis.owncommunity.R
import java.util.UUID

data class HomeStory(
    val id: String,
    @DrawableRes val storyImage: Int = R.drawable.demo_story_1,
    @DrawableRes val profileImage: Int = R.drawable.demo_profile_1,
    val isRead: Boolean = false,
) {
    companion object {
        val EMPTY = HomeStory(
            id = UUID.randomUUID().toString(),
            storyImage = 0,
            profileImage = 0,
            isRead = false,
        )
    }
}
