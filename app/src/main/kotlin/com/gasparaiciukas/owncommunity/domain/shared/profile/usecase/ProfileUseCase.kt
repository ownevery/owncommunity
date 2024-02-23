package com.gasparaiciukas.owncommunity.domain.shared.profile.usecase

import com.gasparaiciukas.owncommunity.R
import com.gasparaiciukas.owncommunity.domain.shared.profile.model.Profile
import java.util.UUID
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

class ProfileUseCase {
    fun getSuggestedProfiles(): PersistentList<Profile> = suggestedProfiles

    fun getMyProfile(): Profile = myProfile

    companion object {
        val myProfile =
            Profile(
                coverImage = R.drawable.demo_my_profile_cover,
                profileImage = R.drawable.demo_my_profile_photo,
                displayName = "Alex Tsimikas",
                followerCount = 2467,
                followingCount = 1589,
                id = UUID.randomUUID().toString(),
                isFollowed = false,
                locationName = "Brooklyn, NY",
                shortBiography = "Writer by Profession. Artist by Passion!",
            )

        private val profile1 =
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
            )
        private val profile2 =
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
            )
        private val profile3 =
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
            )
        private val profile4 =
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
            )
        private val profile5 =
            Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_5,
                displayName = "Tony Monta",
                followerCount = 0,
                isFollowed = false,
                coverImage = 0,
                followingCount = 0,
                locationName = "",
                shortBiography = "",
            )
        private val profile6 =
            Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_6,
                displayName = "Jessica Thompson",
                followerCount = 199_317,
                isFollowed = true,
                coverImage = 0,
                followingCount = 0,
                locationName = "",
                shortBiography = "",
            )
        private val profile7 =
            Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_7,
                displayName = "Dustin Grant",
                followerCount = 39,
                isFollowed = false,
                coverImage = 0,
                followingCount = 0,
                locationName = "",
                shortBiography = "",
            )
        private val suggestedProfiles =
            persistentListOf(profile1, profile2, profile3, profile4, profile5, profile6, profile7)
    }
}
