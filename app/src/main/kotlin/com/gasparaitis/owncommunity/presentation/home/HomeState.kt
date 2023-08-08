package com.gasparaitis.owncommunity.presentation.home

import com.gasparaitis.owncommunity.domain.home.model.HomePost
import com.gasparaitis.owncommunity.domain.home.model.HomeStory

data class HomeState(
    val areAllAlertsRead: Boolean,
    val posts: List<HomePost>,
    val stories: List<HomeStory>,
) {
    companion object {
        val EMPTY = HomeState(
            areAllAlertsRead = false,
            posts = listOf(),
            stories = listOf(),
        )
    }
}
