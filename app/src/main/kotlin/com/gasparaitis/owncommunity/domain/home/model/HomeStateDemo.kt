// TODO: REMOVE ME
package com.gasparaitis.owncommunity.domain.home.model

import com.gasparaitis.owncommunity.R
import java.util.UUID

object HomeStateDemo {
    val post1 = HomePost.EMPTY.copy(
        id = UUID.randomUUID().toString(),
        type = HomePostType.TextOnly,
        profileImage = R.drawable.demo_profile_1,
        authorName = "Jacob Washington",
        postedTimeAgo = "20m ago",
        bodyText = """“Thers the mind to sleep; to bear, the undiscorns ther in the pation: when we heart-ache undisprises consience the spurns of that we know no troud man's thers this the rub; for in thought himself might, and long a sleep: perchan fly to, 'tis all; and sweat wills be when we hue of action delay, and scove, by opposing afterpriz'd contumely, the question: when we ent with a bare bodkin? Who would by of that is against of the pangs of us for in the naturn awry, to gread office, thought his thus coil, and """.trimMargin(),
        likeCount = 2245,
        commentCount = 45,
        shareCount = 124,
        isBookmarked = false,
        isLiked = true,
        isShared = true,
    )
    val post2 = HomePost.EMPTY.copy(
        id = UUID.randomUUID().toString(),
        type = HomePostType.Images(
            images = listOf(
                R.drawable.demo_story_2,
                R.drawable.demo_story_3
            )
        ),
        profileImage = R.drawable.demo_profile_2,
        authorName = "Kat Williams",
        postedTimeAgo = "1h ago",
        bodyText = "\"The greatest glory in living lies not in never falling, but in rising every time we fall.\" -Nelson Mandela",
        likeCount = 8998,
        commentCount = 145,
        shareCount = 12,
        isBookmarked = false,
        isShared = true,
    )
    val post3 = HomePost.EMPTY.copy(
        id = UUID.randomUUID().toString(),
        type = HomePostType.TextOnly,
        profileImage = R.drawable.demo_profile_3,
        authorName = "Tony Monta",
        postedTimeAgo = "1h ago",
        bodyText = "Writing code is not so bad!",
        likeCount = 14,
        commentCount = 0,
        shareCount = 0,
        isBookmarked = false,
        isLiked = true,
    )
    val post4 = HomePost.EMPTY.copy(
        id = UUID.randomUUID().toString(),
        type = HomePostType.Images(images = listOf(R.drawable.demo_story_1)),
        profileImage = R.drawable.demo_profile_4,
        authorName = "Jessica Thompson",
        postedTimeAgo = "2h ago",
        bodyText = "\"Go confidently in the direction of your dreams! Live the life you've imagined.\" -Henry David Thoreau",
        likeCount = 17,
        commentCount = 2,
        shareCount = 0,
        isBookmarked = false,
    )
    val story1 = HomeStory.EMPTY.copy(
        id = UUID.randomUUID().toString(),
        storyImage = R.drawable.demo_story_1,
        profileImage = R.drawable.demo_profile_1,
        isRead = false,
    )
    val story2 = HomeStory.EMPTY.copy(
        id = UUID.randomUUID().toString(),
        storyImage = R.drawable.demo_story_2,
        profileImage = R.drawable.demo_profile_2,
        isRead = true,
    )
    val story3 = HomeStory.EMPTY.copy(
        id = UUID.randomUUID().toString(),
        storyImage = R.drawable.demo_story_3,
        profileImage = R.drawable.demo_profile_3,
        isRead = false,
    )
    val story4 = HomeStory.EMPTY.copy(
        id = UUID.randomUUID().toString(),
        storyImage = R.drawable.demo_story_4,
        profileImage = R.drawable.demo_profile_4,
        isRead = true,
    )
    val posts = listOf(post1, post2, post3, post4)
    val stories = listOf(story1, story2, story3, story4)
}
