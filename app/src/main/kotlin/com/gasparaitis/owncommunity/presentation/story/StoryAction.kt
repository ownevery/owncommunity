package com.gasparaitis.owncommunity.presentation.story

import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile

sealed class StoryAction {
    object OnStoryReplyBarClick : StoryAction()
    object OnStoryReplySend : StoryAction()
    class OnStoryReplyQueryChange(val query: String) : StoryAction()
    class OnTabSelected(val index: Int) : StoryAction()
    class OnProfileClick(val profile: Profile) : StoryAction()
}
