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

object HomeUiStateDemo {
    val post1 = HomeItem(
        type = HomeItemType.TextOnly,
        profileImage = R.drawable.demo_profile_1,
        authorName = "Jacob Washington",
        postedTimeAgo = "20m ago",
        bodyText = """“Thers the mind to sleep; to bear, the undiscorns ther in the pation: when we heart-ache undisprises consience the spurns of that we know no troud man's thers this the rub; for in thought himself might, and long a sleep: perchan fly to, 'tis all; and sweat wills be when we hue of action delay, and scove, by opposing afterpriz'd contumely, the question: when we ent with a bare bodkin? Who would by of that is against of the pangs of us for in the naturn awry, to gread office, thought his thus coil, and """.trimMargin(),
        likeCount = "2,245",
        commentCount = "45",
        shareCount = "124",
        isBookmarked = false,
        isLiked = true,
        isShared = true
    )
    val post2 = HomeItem(
        type = HomeItemType.Images(images = listOf(R.drawable.demo_story_2, R.drawable.demo_story_3)),
        profileImage = R.drawable.demo_profile_2,
        authorName = "Kat Williams",
        postedTimeAgo = "1h ago",
        bodyText = "\"The greatest glory in living lies not in never falling, but in rising every time we fall.\" -Nelson Mandela",
        likeCount = "8,998",
        commentCount = "145",
        shareCount = "12",
        isBookmarked = false,
        isShared = true,
    )
    val post3 = HomeItem(
        type = HomeItemType.TextOnly,
        profileImage = R.drawable.demo_profile_3,
        authorName = "Tony Monta",
        postedTimeAgo = "1h ago",
        bodyText = "Writing code is not so bad!",
        likeCount = "14",
        commentCount = "",
        shareCount = "",
        isBookmarked = false,
        isLiked = true,
    )
    val post4 = HomeItem(
        type = HomeItemType.Images(images = listOf(R.drawable.demo_story_1)),
        profileImage = R.drawable.demo_profile_4,
        authorName = "Jessica Thompson",
        postedTimeAgo = "2h ago",
        bodyText = "\"Go confidently in the direction of your dreams! Live the life you've imagined.\" -Henry David Thoreau",
        likeCount = "17",
        commentCount = "2",
        shareCount = "",
        isBookmarked = false,
    )
    val story1 = HomeStory(
        storyImage = R.drawable.demo_story_1,
        profileImage = R.drawable.demo_profile_1,
        isRead = false,
    )
    val story2 = HomeStory(
        storyImage = R.drawable.demo_story_2,
        profileImage = R.drawable.demo_profile_2,
        isRead = true,
    )
    val story3 = HomeStory(
        storyImage = R.drawable.demo_story_3,
        profileImage = R.drawable.demo_profile_3,
        isRead = false,
    )
    val story4 = HomeStory(
        storyImage = R.drawable.demo_story_4,
        profileImage = R.drawable.demo_profile_4,
        isRead = true,
    )
}
