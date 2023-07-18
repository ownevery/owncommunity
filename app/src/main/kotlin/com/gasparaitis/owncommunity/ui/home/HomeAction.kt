package com.gasparaitis.owncommunity.ui.home

import com.gasparaitis.owncommunity.utils.home.entity.HomePost
import com.gasparaitis.owncommunity.utils.home.entity.HomeStory

sealed class HomeAction {
    object OnNotificationIconClick : HomeAction()
    data class OnStoryClick(val item: HomeStory) : HomeAction()
    data class OnPostLikeClick(val item: HomePost) : HomeAction()
    data class OnPostBodyClick(val item: HomePost) : HomeAction()
    data class OnPostCommentClick(val item: HomePost) : HomeAction()
    data class OnPostShareClick(val item: HomePost) : HomeAction()
    data class OnPostBookmarkClick(val item: HomePost) : HomeAction()
    data class OnPostAuthorClick(val item: HomePost) : HomeAction()
}
