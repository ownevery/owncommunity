package com.gasparaitis.owncommunity.presentation.home

import com.gasparaitis.owncommunity.domain.shared.story.model.Story
import com.gasparaitis.owncommunity.presentation.shared.composables.post.PostAction

sealed class HomeAction {
    object OnAlertIconClick : HomeAction()
    data class OnStoryClick(val item: Story) : HomeAction()
    data class OnPostAction(val postAction: PostAction) : HomeAction()
}
