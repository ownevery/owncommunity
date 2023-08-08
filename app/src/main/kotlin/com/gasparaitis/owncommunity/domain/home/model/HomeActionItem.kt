package com.gasparaitis.owncommunity.domain.home.model

import androidx.annotation.DrawableRes
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.presentation.home.HomeAction

data class HomeActionItem(
    @DrawableRes val icon: Int,
    val description: String,
    val count: String,
    val isActive: Boolean,
    val action: HomeAction,
) {
    companion object {
        val EMPTY = HomeActionItem(
            icon = 0,
            description = "",
            count = "",
            isActive = false,
            action = HomeAction.OnPostShareClick(HomePost.EMPTY),
        )
        fun actions(item: HomePost): List<HomeActionItem> = listOf(
            HomeActionItem(
                icon = R.drawable.ic_like,
                description = "Like",
                count = item.likeCount.toString(),
                isActive = item.isLiked,
                action = HomeAction.OnPostLikeClick(item),
            ),
            HomeActionItem(
                icon = R.drawable.ic_comment,
                description = "Comment",
                count = item.commentCount.toString(),
                isActive = false,
                action = HomeAction.OnPostCommentClick(item),
            ),
            HomeActionItem(
                icon = R.drawable.ic_share,
                description = "Share",
                count = item.shareCount.toString(),
                isActive = item.isShared,
                action = HomeAction.OnPostShareClick(item),
            ),
        )
    }
}
