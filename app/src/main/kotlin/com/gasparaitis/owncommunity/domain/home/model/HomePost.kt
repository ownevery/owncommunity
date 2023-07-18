package com.gasparaitis.owncommunity.domain.home.model

import androidx.annotation.DrawableRes

data class HomePost(
    val id: String,
    val type: HomePostType,
    @DrawableRes val profileImage: Int,
    val authorName: String,
    val postedTimeAgo: String,
    val bodyText: String,
    val likeCount: Int,
    val commentCount: Int,
    val shareCount: Int,
    val isLiked: Boolean,
    val isShared: Boolean,
    val isBookmarked: Boolean,
) {
    companion object {
        val EMPTY = HomePost(
            id = "",
            type = HomePostType.TextOnly,
            profileImage = 0,
            authorName = "",
            postedTimeAgo = "",
            bodyText = "",
            likeCount = 0,
            commentCount = 0,
            shareCount = 0,
            isLiked = false,
            isShared = false,
            isBookmarked = false,
        )
    }
}

sealed class HomePostType {
    object TextOnly : HomePostType()
    class Images(val images: List<Int>) : HomePostType()
}
