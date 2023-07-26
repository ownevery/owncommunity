package com.gasparaitis.owncommunity.presentation.home

import com.gasparaitis.owncommunity.presentation.destinations.AlertsScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.DirectionDestination
import com.gasparaitis.owncommunity.presentation.destinations.HomeScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.PostScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.ProfileScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.StoryScreenDestination

sealed class HomeNavEvent(
    val destination: DirectionDestination,
) {
    object OpenAlerts : HomeNavEvent(destination = AlertsScreenDestination)
    object OpenStory : HomeNavEvent(destination = StoryScreenDestination)
    object OpenPostAuthorProfile : HomeNavEvent(destination = ProfileScreenDestination)
    object OpenPost : HomeNavEvent(destination = PostScreenDestination)
    object ScrollUp : HomeNavEvent(destination = HomeScreenDestination)
}
