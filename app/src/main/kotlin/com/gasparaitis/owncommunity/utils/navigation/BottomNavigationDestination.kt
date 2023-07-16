package com.gasparaitis.owncommunity.utils.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.ui.destinations.CreateScreenDestination
import com.gasparaitis.owncommunity.ui.destinations.HomeScreenDestination
import com.gasparaitis.owncommunity.ui.destinations.NotificationsScreenDestination
import com.gasparaitis.owncommunity.ui.destinations.ProfileScreenDestination
import com.gasparaitis.owncommunity.ui.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomNavigationDestination(
    val direction: DirectionDestinationSpec,
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
) {
    // #1
    Home(HomeScreenDestination, R.drawable.ic_feed, R.string.bottom_navigation_home),

    // #2
    Search(SearchScreenDestination, R.drawable.ic_search, R.string.bottom_navigation_search),

    // #3
    Create(CreateScreenDestination, R.drawable.ic_create, R.string.bottom_navigation_create),

    // #4
    Notifications(NotificationsScreenDestination, R.drawable.ic_alert, R.string.bottom_navigation_notifications),

    // #5
    Profile(ProfileScreenDestination, R.drawable.ic_profile, R.string.bottom_navigation_profile),
}
