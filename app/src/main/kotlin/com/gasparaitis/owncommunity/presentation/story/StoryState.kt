package com.gasparaitis.owncommunity.presentation.story

import androidx.compose.ui.text.input.TextFieldValue
import com.gasparaitis.owncommunity.domain.shared.story.model.Story

data class StoryState(
    val selectedTabIndex: Int,
    val searchText: TextFieldValue,
    val stories: List<Story>,
) {
    companion object {
        val EMPTY = StoryState(
            selectedTabIndex = 0,
            searchText = TextFieldValue(),
            stories = listOf(),
        )
    }
}
