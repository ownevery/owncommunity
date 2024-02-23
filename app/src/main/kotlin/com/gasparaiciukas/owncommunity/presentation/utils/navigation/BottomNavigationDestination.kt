package com.gasparaiciukas.owncommunity.presentation.utils.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gasparaiciukas.owncommunity.R
import com.gasparaiciukas.owncommunity.presentation.destinations.AlertListScreenDestination
import com.gasparaiciukas.owncommunity.presentation.destinations.CreateScreenDestination
import com.gasparaiciukas.owncommunity.presentation.destinations.Destination
import com.gasparaiciukas.owncommunity.presentation.destinations.HomeScreenDestination
import com.gasparaiciukas.owncommunity.presentation.destinations.ProfileScreenDestination
import com.gasparaiciukas.owncommunity.presentation.destinations.SearchScreenDestination

enum class BottomNavigationDestination(
    val direction: Destination,
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
    Alerts(AlertListScreenDestination, R.drawable.ic_alert, R.string.bottom_navigation_alerts),

    // #5
    Profile(ProfileScreenDestination, R.drawable.ic_profile, R.string.bottom_navigation_profile),
}
