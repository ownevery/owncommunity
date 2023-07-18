package com.gasparaitis.owncommunity.utils.home.entity

import androidx.annotation.DrawableRes
import com.gasparaitis.owncommunity.R
import java.util.UUID

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
            id = UUID.randomUUID().toString(),
            type = HomePostType.TextOnly,
            profileImage = R.drawable.ic_profile,
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
