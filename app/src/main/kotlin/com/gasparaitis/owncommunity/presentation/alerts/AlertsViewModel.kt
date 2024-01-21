package com.gasparaitis.owncommunity.presentation.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasparaitis.owncommunity.domain.alerts.model.AlertItem
import com.gasparaitis.owncommunity.domain.alerts.usecase.AlertsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val alertsUseCase: AlertsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AlertsState.EMPTY)
    val uiState = _uiState.asStateFlow()

    init {
        onCreated()
    }

    private fun onCreated() {
        val alerts = alertsUseCase.getAlerts()
        _uiState.update { state ->
            state.copy(alertMap = alerts)
        }
    }

    fun onEvent(event: AlertsState.Event) {
        when (event) {
            AlertsState.OnMarkAllAsReadClick -> onMarkAllAsReadClick()
            is AlertsState.OnAlertItemClick -> onAlertItemClick(event.item)
            AlertsState.NavigateToPostScreen -> {}
            AlertsState.NavigateToProfileScreen -> {}
        }
    }

    fun onEventHandled(event: AlertsState.Event?) {
        _uiState.update { state ->
            if (state.event == event) {
                state.copy(
                    event = null,
                )
            } else {
                state
            }
        }
    }

    private fun onAlertItemClick(item: AlertItem) {
        viewModelScope.launch {
            _uiState.update { state ->
                val items =
                    state.alertMap.mutate { map ->
                        map.compute(item.section) { _, value ->
                            value?.mutate { alertList ->
                                alertList.map { listItem ->
                                    if (listItem.id == item.id) {
                                        listItem.copy(isRead = true)
                                    } else {
                                        listItem
                                    }
                                }
                            }
                        }
                    }
                val event =
                    if (item.type == AlertItem.Type.Birthday) {
                        AlertsState.NavigateToProfileScreen
                    } else {
                        AlertsState.NavigateToPostScreen
                    }
                state.copy(
                    alertMap = items,
                    event = event,
                )
            }
        }
    }

    private fun onMarkAllAsReadClick() {
        _uiState.update { state ->
            val items =
                state.alertMap.mutate { map ->
                    map.forEach { (_, value) ->
                        value.mutate { alertList ->
                            alertList.map { listItem ->
                                listItem.copy(isRead = true)
                            }
                        }
                    }
                }
            state.copy(
                alertMap = items,
            )
        }
    }
}
