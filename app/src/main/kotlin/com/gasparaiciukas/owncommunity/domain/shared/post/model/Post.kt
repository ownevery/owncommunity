package com.gasparaiciukas.owncommunity.domain.shared.post.model

import androidx.annotation.DrawableRes
import kotlinx.collections.immutable.PersistentList

data class Post(
    val id: String,
    val type: Type,
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
        val EMPTY =
            Post(
                id = "",
                type = Type.TextOnly,
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

    sealed interface Event

    data class OnLikeClick(val item: Post) : Event

    data class OnBodyClick(val item: Post) : Event

    data class OnCommentClick(val item: Post) : Event

    data class OnShareClick(val item: Post) : Event

    data class OnBookmarkClick(val item: Post) : Event

    data class OnAuthorClick(val item: Post) : Event

    sealed interface Type {
        data object TextOnly : Type

        data class Images(val images: PersistentList<Int>) : Type
    }
}
