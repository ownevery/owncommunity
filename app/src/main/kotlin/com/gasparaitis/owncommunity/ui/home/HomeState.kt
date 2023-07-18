package com.gasparaitis.owncommunity.ui.home

import com.gasparaitis.owncommunity.utils.home.entity.HomePost
import com.gasparaitis.owncommunity.utils.home.entity.HomeStory

data class HomeState(
    val areAllNotificationsRead: Boolean,
    val posts: List<HomePost>,
    val stories: List<HomeStory>,
) {
    companion object {
        val EMPTY = HomeState(
            areAllNotificationsRead = false,
            posts = listOf(),
            stories = listOf(),
        )
    }
}
