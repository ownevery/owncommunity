package com.gasparaitis.owncommunity.ui.home

sealed class HomeAction {
    data class OnStoryClick(val story: HomeStory) : HomeAction()
    data class OnPostLikeClick(val item: HomeItem) : HomeAction()
    data class OnPostCommentClick(val item: HomeItem) : HomeAction()
    data class OnPostShareClick(val item: HomeItem) : HomeAction()
    data class OnPostBookmarkClick(val item: HomeItem) : HomeAction()
}
