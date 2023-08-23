package com.gasparaitis.owncommunity.presentation.story

import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile

sealed class StoryAction {
    data object OnStoryReplyBarClick : StoryAction()
    data object OnStoryReplySend : StoryAction()
    class OnStoryReplyQueryChange(val query: String) : StoryAction()
    class OnTabSelected(val index: Int) : StoryAction()
    class OnProfileClick(val profile: Profile) : StoryAction()
    class OnStoryGoBack(val storyIndex: Int, val storyItemIndex: Int) : StoryAction()
    class OnStoryGoForward(val storyIndex: Int, val storyItemIndex: Int) : StoryAction()
}
