package com.gasparaitis.owncommunity.domain.shared.post.usecase

import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.domain.shared.post.model.Post
import com.gasparaitis.owncommunity.presentation.utils.extensions.ClockUtils
import java.util.UUID
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

class PostUseCase {
    fun getHomePosts(): PersistentList<Post> = homePosts

    fun getTrendingPosts(): PersistentList<Post> = trendingPosts

    fun getLatestPosts(): PersistentList<Post> = latestPosts

    companion object {
        private val homePost1 =
            Post.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                type = Post.Type.TextOnly,
                profileImage = R.drawable.demo_profile_picture_1,
                authorName = "Jacob Washington",
                postedAtTimestamp = ClockUtils.shiftCurrentTime((-10).minutes),
                bodyText = """“Thers the mind to sleep; to bear, the undiscorns ther in the pation: when we heart-ache undisprises consience the spurns of that we know no troud man's thers this the rub; for in thought himself might, and long a sleep: perchan fly to, 'tis all; and sweat wills be when we hue of action delay, and scove, by opposing afterpriz'd contumely, the question: when we ent with a bare bodkin? Who would by of that is against of the pangs of us for in the naturn awry, to gread office, thought his thus coil, and """.trimMargin(),
                likeCount = 2245,
                commentCount = 45,
                shareCount = 124,
                isBookmarked = false,
                isLiked = true,
                isShared = true,
            )
        private val homePost2 =
            Post.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                type =
                    Post.Type.Images(
                        images =
                            persistentListOf(
                                R.drawable.demo_story_2,
                                R.drawable.demo_story_3,
                            ),
                    ),
                profileImage = R.drawable.demo_profile_picture_2,
                authorName = "Kat Williams",
                postedAtTimestamp = ClockUtils.shiftCurrentTime((-1).hours),
                bodyText = "\"The greatest glory in living lies not in never falling, but in rising every time we fall.\" -Nelson Mandela",
                likeCount = 8998,
                commentCount = 145,
                shareCount = 12,
                isBookmarked = false,
                isShared = true,
            )
        private val homePost3 =
            Post.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                type = Post.Type.TextOnly,
                profileImage = R.drawable.demo_profile_picture_3,
                authorName = "Tony Monta",
                postedAtTimestamp = ClockUtils.shiftCurrentTime((-2).hours),
                bodyText = "Writing code is not so bad!",
                likeCount = 14,
                commentCount = 0,
                shareCount = 0,
                isBookmarked = false,
                isLiked = true,
            )
        private val homePost4 =
            Post.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                type = Post.Type.Images(images = persistentListOf(R.drawable.demo_story_1)),
                profileImage = R.drawable.demo_profile_picture_4,
                authorName = "Jessica Thompson",
                postedAtTimestamp = ClockUtils.shiftCurrentTime((-4).hours),
                bodyText = "\"Go confidently in the direction of your dreams! Live the life you've imagined.\" -Henry David Thoreau",
                likeCount = 17,
                commentCount = 2,
                shareCount = 0,
                isBookmarked = false,
            )
        private val homePosts = persistentListOf(homePost1, homePost2, homePost3, homePost4)

        private val trendingPost1 =
            Post.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                type =
                    Post.Type.Images(
                        images =
                            persistentListOf(
                                R.drawable.demo_profile_14,
                                R.drawable.demo_profile_15,
                            ),
                    ),
                profileImage = R.drawable.demo_profile_picture_1,
                authorName = "Michelle Ogilvy",
                postedAtTimestamp = ClockUtils.shiftCurrentTime((-2).hours),
                bodyText = "",
                likeCount = 18_600,
                commentCount = 4_789,
                shareCount = 12_182,
                isBookmarked = false,
            )
        private val trendingPost2 =
            Post.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                type =
                    Post.Type.Images(
                        images =
                            persistentListOf(
                                R.drawable.demo_profile_12,
                                R.drawable.demo_profile_13,
                            ),
                    ),
                profileImage = R.drawable.demo_profile_picture_2,
                authorName = "Brandon Loia",
                postedAtTimestamp = ClockUtils.shiftCurrentTime((-2).hours),
                bodyText = "",
                likeCount = 4_762,
                commentCount = 186,
                shareCount = 2_891,
                isBookmarked = false,
            )
        private val trendingPosts = persistentListOf(trendingPost1, trendingPost2)

        private val latestPost1 =
            Post.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                type =
                    Post.Type.Images(
                        images =
                            persistentListOf(
                                R.drawable.demo_latest_1,
                                R.drawable.demo_latest_2,
                                R.drawable.demo_latest_3,
                            ),
                    ),
                profileImage = R.drawable.demo_profile_picture_1,
                authorName = "Kate Williams",
                postedAtTimestamp = ClockUtils.shiftCurrentTime((-1).hours),
                bodyText = "",
                likeCount = 8_998,
                commentCount = 145,
                shareCount = 12,
                isBookmarked = false,
                isLiked = true,
                isShared = true,
            )
        private val latestPost2 =
            Post.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                type = Post.Type.TextOnly,
                profileImage = R.drawable.demo_profile_picture_2,
                authorName = "Jacob Washington",
                postedAtTimestamp = ClockUtils.shiftCurrentTime((-1).hours),
                bodyText = "“If you think you are too small to make a difference, try sleeping with a mosquito.” ~ Dalai Lama",
                likeCount = 2_245,
                commentCount = 45,
                shareCount = 124,
                isBookmarked = false,
                isShared = true,
            )
        private val latestPost3 =
            Post.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                type = Post.Type.Images(images = persistentListOf(R.drawable.demo_profile_9)),
                profileImage = R.drawable.demo_profile_picture_3,
                authorName = "Dustin Grant",
                postedAtTimestamp = ClockUtils.shiftCurrentTime((-2).hours),
                likeCount = 238,
                commentCount = 14,
                shareCount = 4,
                isBookmarked = false,
                isLiked = true,
            )
        private val latestPosts = persistentListOf(latestPost1, latestPost2, latestPost3)
    }
}
