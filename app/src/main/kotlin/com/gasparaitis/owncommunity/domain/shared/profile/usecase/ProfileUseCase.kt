package com.gasparaitis.owncommunity.domain.shared.profile.usecase

import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.domain.shared.profile.model.Profile
import java.util.UUID
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

class ProfileUseCase {
    fun getSuggestedProfiles(): PersistentList<Profile> = suggestedProfiles

    companion object {
        private val profile1 =
            Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_1,
                displayName = "Michelle Ogilvy",
                followerCount = 1_435,
                isFollowed = false,
            )
        private val profile2 =
            Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_2,
                displayName = "Brandon Loia",
                followerCount = 3_145_017,
                isFollowed = true,
            )
        private val profile3 =
            Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_3,
                displayName = "Jacob Washington",
                followerCount = 30,
                isFollowed = true,
            )
        private val profile4 =
            Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_4,
                displayName = "Kate Williams",
                followerCount = 1,
                isFollowed = true,
            )
        private val profile5 =
            Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_5,
                displayName = "Tony Monta",
                followerCount = 0,
                isFollowed = false,
            )
        private val profile6 =
            Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_6,
                displayName = "Jessica Thompson",
                followerCount = 199_317,
                isFollowed = true,
            )
        private val profile7 =
            Profile(
                id = UUID.randomUUID().toString(),
                profileImage = R.drawable.demo_profile_7,
                displayName = "Dustin Grant",
                followerCount = 39,
                isFollowed = false,
            )
        private val suggestedProfiles =
            persistentListOf(profile1, profile2, profile3, profile4, profile5, profile6, profile7)
    }
}
