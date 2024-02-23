package com.gasparaiciukas.owncommunity.presentation.main.bottomnavigation

import androidx.compose.runtime.Stable

@Stable
data class BottomNavigationState(
    val event: Event?,
) {
    companion object {
        val EMPTY =
            BottomNavigationState(
                event = null,
            )
    }

    sealed interface Event

    data object OnHomeIconRepeatClick : Event
}
