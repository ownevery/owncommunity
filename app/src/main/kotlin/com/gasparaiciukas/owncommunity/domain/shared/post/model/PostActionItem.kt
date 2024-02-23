package com.gasparaiciukas.owncommunity.domain.shared.post.model

import androidx.annotation.DrawableRes
import com.gasparaiciukas.owncommunity.R
import com.gasparaiciukas.owncommunity.presentation.utils.extensions.humanReadableActionCount
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class PostActionItem(
    @DrawableRes val icon: Int,
    val description: String,
    val count: String,
    val isActive: Boolean,
    val action: Post.Event,
) {
    companion object {
        val EMPTY =
            PostActionItem(
                icon = 0,
                description = "",
                count = "",
                isActive = false,
                action = Post.OnShareClick(Post.EMPTY),
            )

        fun actions(item: Post): PersistentList<PostActionItem> =
            persistentListOf(
                PostActionItem(
                    icon = R.drawable.ic_like,
                    description = "Like",
                    count = item.likeCount.humanReadableActionCount,
                    isActive = item.isLiked,
                    action = Post.OnLikeClick(item),
                ),
                PostActionItem(
                    icon = R.drawable.ic_comment,
                    description = "Comment",
                    count = item.commentCount.humanReadableActionCount,
                    isActive = false,
                    action = Post.OnCommentClick(item),
                ),
                PostActionItem(
                    icon = R.drawable.ic_share,
                    description = "Share",
                    count = item.shareCount.humanReadableActionCount,
                    isActive = item.isShared,
                    action = Post.OnShareClick(item),
                ),
            )
    }
}
