package com.gasparaitis.owncommunity.presentation.search

import androidx.compose.ui.text.input.TextFieldValue
import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile

data class SearchState(
    val selectedTabIndex: Int,
    val searchText: TextFieldValue,
    val trendingPosts: List<Post>,
    val latestPosts: List<Post>,
    val profiles: List<Profile>,
) {
    companion object {
        val EMPTY = SearchState(
            selectedTabIndex = 0,
            searchText = TextFieldValue(),
            trendingPosts = listOf(),
            latestPosts = listOf(),
            profiles = listOf(),
        )
    }
}
