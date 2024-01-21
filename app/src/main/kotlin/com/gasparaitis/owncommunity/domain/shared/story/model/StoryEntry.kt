package com.gasparaitis.owncommunity.domain.shared.story.model

import androidx.annotation.DrawableRes

data class StoryEntry(
    @DrawableRes val drawableResId: Int,
    val isSeen: Boolean,
) {
    companion object {
        val EMPTY =
            StoryEntry(
                drawableResId = 0,
                isSeen = false,
            )
    }
}
