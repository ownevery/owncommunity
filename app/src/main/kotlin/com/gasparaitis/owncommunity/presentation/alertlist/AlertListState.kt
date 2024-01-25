package com.gasparaitis.owncommunity.presentation.alertlist

import androidx.compose.runtime.Stable
import com.gasparaitis.owncommunity.domain.alerts.model.AlertItem
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

@Stable
data class AlertListState(
    val alertMap: PersistentMap<AlertItem.Section, PersistentList<AlertItem>>,
    val event: Event?,
) {
    companion object {
        val EMPTY =
            AlertListState(
                alertMap = persistentMapOf(),
                event = null,
            )
    }

    sealed interface Event

    data class OnAlertItemClick(val item: AlertItem) : Event

    data object OnMarkAllAsReadClick : Event

    data object NavigateToPostScreen : Event

    data object NavigateToProfileScreen : Event
}
