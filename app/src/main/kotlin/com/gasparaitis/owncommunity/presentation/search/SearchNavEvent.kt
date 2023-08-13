package com.gasparaitis.owncommunity.presentation.search

import com.gasparaitis.owncommunity.presentation.destinations.DirectionDestination
import com.gasparaitis.owncommunity.presentation.destinations.HomeScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.PostScreenDestination
import com.gasparaitis.owncommunity.presentation.destinations.ProfileScreenDestination

sealed class SearchNavEvent(
    val destination: DirectionDestination,
) {
    object OpenPostAuthorProfile : SearchNavEvent(destination = ProfileScreenDestination)
    object OpenPost : SearchNavEvent(destination = PostScreenDestination)
    object ScrollUp : SearchNavEvent(destination = HomeScreenDestination)
}
