package com.gasparaitis.owncommunity.domain.search.usecase

import com.gasparaitis.owncommunity.domain.shared.post.usecase.PostUseCase
import com.gasparaitis.owncommunity.domain.shared.profile.usecase.ProfileUseCase
import com.gasparaitis.owncommunity.presentation.search.SearchState

class SearchUseCase(
    private val postUseCase: PostUseCase,
    private val profileUseCase: ProfileUseCase,
) {
    fun getState(): SearchState = SearchState.EMPTY.copy(
        trendingPosts = postUseCase.getTrendingPosts(),
        latestPosts = postUseCase.getLatestPosts(),
        profiles = profileUseCase.getSuggestedProfiles(),
    )
}
