package com.gasparaitis.owncommunity.domain.shared.post.model

import androidx.annotation.DrawableRes

data class Post(
    val id: String,
    val type: PostType,
    @DrawableRes val profileImage: Int,
    val authorName: String,
    val postedAtTimestamp: Long,
    val bodyText: String,
    val likeCount: Long,
    val commentCount: Long,
    val shareCount: Long,
    val isLiked: Boolean,
    val isShared: Boolean,
    val isBookmarked: Boolean,
) {
    companion object {
        val EMPTY = Post(
            id = "",
            type = PostType.TextOnly,
            profileImage = 0,
            authorName = "",
            postedAtTimestamp = 0,
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

sealed class PostType {
    object TextOnly : PostType()
    class Images(val images: List<Int>) : PostType()
}
