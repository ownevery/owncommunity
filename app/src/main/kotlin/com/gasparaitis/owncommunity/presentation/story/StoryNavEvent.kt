package com.gasparaitis.owncommunity.presentation.story

import com.gasparaitis.owncommunity.presentation.destinations.DirectionDestination
import com.gasparaitis.owncommunity.presentation.destinations.ProfileScreenDestination

sealed class StoryNavEvent(
    val destination: DirectionDestination,
) {
    object OpenProfile : StoryNavEvent(destination = ProfileScreenDestination)
}
