package com.gasparaiciukas.owncommunity.domain.shared.story.model

import androidx.annotation.DrawableRes

data class StoryEntry(
    @DrawableRes val drawableResId: Int,
) {
    companion object {
        val EMPTY =
            StoryEntry(
                drawableResId = 0,
            )
    }
}
