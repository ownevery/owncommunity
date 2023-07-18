package com.gasparaitis.owncommunity.presentation.alerts

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class AlertsViewModel : ViewModel() {
    private val _state: MutableStateFlow<AlertsState> = MutableStateFlow(AlertsState.EMPTY)
    val state: StateFlow<AlertsState> = _state.asStateFlow()

    private val _navEvent = MutableSharedFlow<AlertsNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    init {
        onCreated()
    }

    private fun onCreated() {
        _state.value = _state.value.copy(
            areAllAlertsRead = false,
        )
    }

    fun onAction(action: AlertsAction) {
        when (action) {
            AlertsAction.OnAlertItemClick -> TODO()
            AlertsAction.OnMarkAllAsReadClick -> TODO()
        }
    }
}
