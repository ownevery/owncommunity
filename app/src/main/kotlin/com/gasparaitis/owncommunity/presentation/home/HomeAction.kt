package com.gasparaitis.owncommunity.presentation.home

import com.gasparaitis.owncommunity.domain.home.model.HomePost
import com.gasparaitis.owncommunity.domain.home.model.HomeStory

sealed class HomeAction {
    object OnAlertIconClick : HomeAction()
    object OnHomeIconRepeatClick : HomeAction()
    data class OnStoryClick(val item: HomeStory) : HomeAction()
    data class OnPostLikeClick(val item: HomePost) : HomeAction()
    data class OnPostBodyClick(val item: HomePost) : HomeAction()
    data class OnPostCommentClick(val item: HomePost) : HomeAction()
    data class OnPostShareClick(val item: HomePost) : HomeAction()
    data class OnPostBookmarkClick(val item: HomePost) : HomeAction()
    data class OnPostAuthorClick(val item: HomePost) : HomeAction()
}
