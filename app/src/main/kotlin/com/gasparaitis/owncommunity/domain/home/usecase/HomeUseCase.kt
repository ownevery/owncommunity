package com.gasparaitis.owncommunity.domain.home.usecase

import com.gasparaitis.owncommunity.domain.shared.post.usecase.PostUseCase
import com.gasparaitis.owncommunity.domain.shared.story.usecase.StoryUseCase
import com.gasparaitis.owncommunity.presentation.home.HomeState

class HomeUseCase(
    private val postUseCase: PostUseCase,
    private val storyUseCase: StoryUseCase,
) {
    fun getState(): HomeState = HomeState.EMPTY.copy(
        areAllAlertsRead = false,
        posts = postUseCase.getHomePosts(),
        stories = storyUseCase.getStories(),
    )
}
