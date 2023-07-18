package com.gasparaitis.owncommunity.ui.home

import com.gasparaitis.owncommunity.ui.destinations.DirectionDestination
import com.gasparaitis.owncommunity.ui.destinations.NotificationsScreenDestination
import com.gasparaitis.owncommunity.ui.destinations.PostScreenDestination
import com.gasparaitis.owncommunity.ui.destinations.ProfileScreenDestination
import com.gasparaitis.owncommunity.ui.destinations.StoryScreenDestination

sealed class HomeNavEvent(
    val destination: DirectionDestination,
) {
    object OpenNotifications : HomeNavEvent(destination = NotificationsScreenDestination)
    object OpenStory : HomeNavEvent(destination = StoryScreenDestination)
    object OpenPostAuthorProfile : HomeNavEvent(destination = ProfileScreenDestination)
    object OpenPost : HomeNavEvent(destination = PostScreenDestination)
}
