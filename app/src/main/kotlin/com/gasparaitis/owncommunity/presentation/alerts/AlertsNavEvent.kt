package com.gasparaitis.owncommunity.presentation.alerts

import com.gasparaitis.owncommunity.presentation.destinations.DirectionDestination
import com.gasparaitis.owncommunity.presentation.destinations.PostScreenDestination

sealed class AlertsNavEvent(
    val destination: DirectionDestination,
) {
    object OpenPost : AlertsNavEvent(destination = PostScreenDestination)
}
