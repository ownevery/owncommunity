package com.gasparaiciukas.owncommunity.domain.shared.story.usecase

import com.gasparaiciukas.owncommunity.R
import com.gasparaiciukas.owncommunity.domain.shared.profile.model.Profile
import com.gasparaiciukas.owncommunity.domain.shared.story.model.Story
import com.gasparaiciukas.owncommunity.domain.shared.story.model.StoryEntry
import java.util.UUID
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

class StoryUseCase {
    fun getStories(): PersistentList<Story> = stories

    companion object {
        private val story1 =
            Story.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                entries =
                    persistentListOf(
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_1),
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_2),
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_3),
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_4),
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_5),
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_6),
                    ),
                profile =
                    Profile(
                        id = UUID.randomUUID().toString(),
                        profileImage = R.drawable.demo_profile_1,
                        displayName = "Michelle Ogilvy",
                        followerCount = 1_435,
                        isFollowed = false,
                        coverImage = 0,
                        followingCount = 0,
                        locationName = "",
                        shortBiography = "",
                    ),
                isRead = false,
            )
        private val story2 =
            Story.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                entries =
                    persistentListOf(
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_7),
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_8),
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_9),
                    ),
                profile =
                    Profile(
                        id = UUID.randomUUID().toString(),
                        profileImage = R.drawable.demo_profile_2,
                        displayName = "Brandon Loia",
                        followerCount = 3_145_017,
                        isFollowed = true,
                        coverImage = 0,
                        followingCount = 0,
                        locationName = "",
                        shortBiography = "",
                    ),
                isRead = true,
            )
        private val story3 =
            Story.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                entries =
                    persistentListOf(
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_10),
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_11),
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_12),
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_13),
                    ),
                profile =
                    Profile(
                        id = UUID.randomUUID().toString(),
                        profileImage = R.drawable.demo_profile_3,
                        displayName = "Jacob Washington",
                        followerCount = 30,
                        isFollowed = true,
                        coverImage = 0,
                        followingCount = 0,
                        locationName = "",
                        shortBiography = "",
                    ),
                isRead = false,
            )
        private val story4 =
            Story.EMPTY.copy(
                id = UUID.randomUUID().toString(),
                entries =
                    persistentListOf(
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_14),
                        StoryEntry.EMPTY.copy(drawableResId = R.drawable.demo_profile_15),
                    ),
                profile =
                    Profile(
                        id = UUID.randomUUID().toString(),
                        profileImage = R.drawable.demo_profile_4,
                        displayName = "Kate Williams",
                        followerCount = 1,
                        isFollowed = true,
                        coverImage = 0,
                        followingCount = 0,
                        locationName = "",
                        shortBiography = "",
                    ),
                isRead = true,
            )
        private val stories = persistentListOf(story1, story2, story3, story4)
    }
}
