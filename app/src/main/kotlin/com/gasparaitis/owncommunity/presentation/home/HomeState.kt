package com.gasparaitis.owncommunity.presentation.home

import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.domain.shared.story.model.Story

data class HomeState(
    val areAllAlertsRead: Boolean,
    val posts: List<Post>,
    val stories: List<Story>,
) {
    companion object {
        val EMPTY = HomeState(
            areAllAlertsRead = false,
            posts = listOf(),
            stories = listOf(),
        )
    }
}
