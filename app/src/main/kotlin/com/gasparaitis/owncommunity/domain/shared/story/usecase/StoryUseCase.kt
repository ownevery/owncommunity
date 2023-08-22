package com.gasparaitis.owncommunity.domain.shared.story.usecase

import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
import com.gasparaitis.owncommunity.domain.shared.story.model.Story
import java.util.UUID

class StoryUseCase {
    fun getStories(): List<Story> = stories

    companion object {
        private val story1 = Story.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            storyImages = listOf(
                R.drawable.demo_profile_1,
                R.drawable.demo_profile_2,
                R.drawable.demo_profile_3,
                R.drawable.demo_profile_4,
                R.drawable.demo_profile_5,
                R.drawable.demo_profile_6,
            ),
            profile = Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_1,
                displayName = "Michelle Ogilvy",
                followerCount = 1_435,
                isFollowed = false,
            ),
            isRead = false,
        )
        private val story2 = Story.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            storyImages = listOf(
                R.drawable.demo_profile_7,
                R.drawable.demo_profile_8,
                R.drawable.demo_profile_9,
            ),
            profile = Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_2,
                displayName = "Brandon Loia",
                followerCount = 3_145_017,
                isFollowed = true,
            ),
            isRead = true,
        )
        private val story3 = Story.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            storyImages = listOf(
                R.drawable.demo_profile_10,
                R.drawable.demo_profile_11,
                R.drawable.demo_profile_12,
                R.drawable.demo_profile_13,
            ),
            profile = Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_3,
                displayName = "Jacob Washington",
                followerCount = 30,
                isFollowed = true,
            ),
            isRead = false,
        )
        private val story4 = Story.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            storyImages = listOf(
                R.drawable.demo_profile_14,
                R.drawable.demo_profile_15,
            ),
            profile = Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_4,
                displayName = "Kate Williams",
                followerCount = 1,
                isFollowed = true,
            ),
            isRead = true,
        )
        private val stories = listOf(story1, story2, story3, story4)
    }
}
