package com.gasparaitis.owncommunity.domain.shared.profile.model

data class Profile(
    val id: String,
    val profileImage: Int,
    val displayName: String,
    val followerCount: Long,
    val isFollowed: Boolean,
) {
    companion object {
        val EMPTY =
            Profile(
                id = "",
                profileImage = 0,
                displayName = "",
                followerCount = 0,
                isFollowed = false,
            )
    }
}
