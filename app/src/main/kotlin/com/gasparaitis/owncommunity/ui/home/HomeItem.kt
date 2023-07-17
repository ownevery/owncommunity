package com.gasparaitis.owncommunity.ui.home

import androidx.annotation.DrawableRes
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.ui.home.HomeUiStateDemo.post1
import com.gasparaitis.owncommunity.ui.home.HomeUiStateDemo.post2
import com.gasparaitis.owncommunity.ui.home.HomeUiStateDemo.post3
import com.gasparaitis.owncommunity.ui.home.HomeUiStateDemo.post4
import com.gasparaitis.owncommunity.ui.home.HomeUiStateDemo.story1
import com.gasparaitis.owncommunity.ui.home.HomeUiStateDemo.story2
import com.gasparaitis.owncommunity.ui.home.HomeUiStateDemo.story3
import com.gasparaitis.owncommunity.ui.home.HomeUiStateDemo.story4
import com.gasparaitis.owncommunity.utils.demo.HomeUiStateDemo.post1
import com.gasparaitis.owncommunity.utils.demo.HomeUiStateDemo.story1
import java.util.UUID

data class HomeUiState(
    val posts: List<HomeItem> = listOf(post1, post2, post3, post4),
    val stories: List<HomeStory> = listOf(story1, story2, story3, story4)
)

data class HomeItem(
    val id: String = UUID.randomUUID().toString(),
    val type: HomeItemType = HomeItemType.TextOnly,
    @DrawableRes val profileImage: Int = R.drawable.ic_profile,
    val authorName: String = "",
    val postedTimeAgo: String = "",
    val bodyText: String = "",
    val likeCount: String = "",
    val commentCount: String = "",
    val shareCount: String = "",
    val isLiked: Boolean = false,
    val isShared: Boolean = false,
    val isBookmarked: Boolean = false,
)

data class HomeAction(
    @DrawableRes val icon: Int,
    val description: String,
    val count: String,
    val isActive: Boolean,
) {
    companion object {
        fun like(count: String, isActive: Boolean) = HomeAction(
            icon = R.drawable.ic_like,
            description = "Like",
            count = count,
            isActive = isActive,
        )
        fun comment(count: String) = HomeAction(
            icon = R.drawable.ic_comment,
            description = "Comment",
            count = count,
            isActive = false,
        )
        fun share(count: String, isActive: Boolean) = HomeAction(
            icon = R.drawable.ic_share,
            description = "Share",
            count = count,
            isActive = isActive,
        )
    }
}

data class HomeStory(
    val id: String = UUID.randomUUID().toString(),
    @DrawableRes val storyImage: Int = R.drawable.demo_story_1,
    @DrawableRes val profileImage: Int = R.drawable.demo_profile_1,
    val isRead: Boolean = false,
)

sealed class HomeItemType {
    object TextOnly : HomeItemType()
    class Images(val images: List<Int>) : HomeItemType()
}
