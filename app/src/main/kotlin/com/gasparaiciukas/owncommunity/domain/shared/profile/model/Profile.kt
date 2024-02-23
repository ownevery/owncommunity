package com.gasparaiciukas.owncommunity.domain.shared.profile.model

import androidx.annotation.DrawableRes

data class Profile(
    @DrawableRes val coverImage: Int,
    @DrawableRes val profileImage: Int,
    val displayName: String,
    val followerCount: Long,
    val followingCount: Long,
    val id: String,
    val isFollowed: Boolean,
    val locationName: String,
    val shortBiography: String,
) {
    companion object {
        val EMPTY =
            Profile(
                coverImage = 0,
                displayName = "",
                followerCount = 0,
                followingCount = 0,
                id = "",
                isFollowed = false,
                locationName = "",
                profileImage = 0,
                shortBiography = "",
            )
    }
}
