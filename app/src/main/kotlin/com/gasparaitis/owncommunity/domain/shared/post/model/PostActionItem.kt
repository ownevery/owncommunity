package com.gasparaitis.owncommunity.domain.shared.post.model

import androidx.annotation.DrawableRes
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.presentation.shared.composables.post.PostAction
import com.gasparaitis.owncommunity.presentation.utils.extensions.humanReadableActionCount

data class PostActionItem(
    @DrawableRes val icon: Int,
    val description: String,
    val count: String,
    val isActive: Boolean,
    val action: PostAction,
) {
    companion object {
        val EMPTY = PostActionItem(
            icon = 0,
            description = "",
            count = "",
            isActive = false,
            action = PostAction.OnShareClick(Post.EMPTY),
        )
        fun actions(item: Post): List<PostActionItem> = listOf(
            PostActionItem(
                icon = R.drawable.ic_like,
                description = "Like",
                count = item.likeCount.humanReadableActionCount,
                isActive = item.isLiked,
                action = PostAction.OnLikeClick(item),
            ),
            PostActionItem(
                icon = R.drawable.ic_comment,
                description = "Comment",
                count = item.commentCount.humanReadableActionCount,
                isActive = false,
                action = PostAction.OnCommentClick(item),
            ),
            PostActionItem(
                icon = R.drawable.ic_share,
                description = "Share",
                count = item.shareCount.humanReadableActionCount,
                isActive = item.isShared,
                action = PostAction.OnShareClick(item),
            ),
        )
    }
}
