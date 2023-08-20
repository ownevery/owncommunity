package com.gasparaitis.owncommunity.domain.shared.story.usecase

import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.domain.shared.story.model.Story
import java.util.UUID

class StoryUseCase {
    fun getStories(): List<Story> = stories

    companion object {
        private val story1 = Story.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            storyImages = listOf(R.drawable.demo_story_1),
            profileImage = R.drawable.demo_profile_picture_1,
            isRead = false,
        )
        private val story2 = Story.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            storyImages = listOf(R.drawable.demo_story_2),
            profileImage = R.drawable.demo_profile_picture_2,
            isRead = true,
        )
        private val story3 = Story.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            storyImages = listOf(R.drawable.demo_story_3),
            profileImage = R.drawable.demo_profile_picture_3,
            isRead = false,
        )
        private val story4 = Story.EMPTY.copy(
            id = UUID.randomUUID().toString(),
            storyImages = listOf(R.drawable.demo_story_4),
            profileImage = R.drawable.demo_profile_picture_4,
            isRead = true,
        )
        private val stories = listOf(story1, story2, story3, story4)
    }
}
