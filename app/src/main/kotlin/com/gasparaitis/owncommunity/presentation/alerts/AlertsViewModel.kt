package com.gasparaitis.owncommunity.presentation.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasparaitis.owncommunity.domain.alerts.model.AlertItem
import com.gasparaitis.owncommunity.domain.alerts.model.AlertItemType
import com.gasparaitis.owncommunity.domain.alerts.usecase.AlertsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val alertsUseCase: AlertsUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<AlertsState> = MutableStateFlow(AlertsState.EMPTY)
    val state: StateFlow<AlertsState> = _state.asStateFlow()

    private val _navEvent = MutableSharedFlow<AlertsNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    init {
        onCreated()
    }

    private fun onCreated() {
        val alerts = alertsUseCase.getAlerts()
        _state.update { state ->
            state.copy(alertItems = alerts)
        }
    }

    fun onAction(action: AlertsAction) {
        when (action) {
            AlertsAction.OnMarkAllAsReadClick -> onMarkAllAsReadClick()
            is AlertsAction.OnAlertItemClick -> onAlertItemClick(action.item)
        }
    }

    private fun onAlertItemClick(
        item: AlertItem,
    ) {
        _state.value = state.value.copy(
            alertItems = state.value.alertItems.toMutableMap().apply {
                compute(item.section) { _, list ->
                    list?.toMutableList()?.map { listItem ->
                        if (listItem.id == item.id) listItem.copy(isRead = true) else listItem
                    }
                }
            }
        )
        viewModelScope.launch {
            _navEvent.emit(
                if (item.type == AlertItemType.BIRTHDAY) AlertsNavEvent.OpenProfile else AlertsNavEvent.OpenPost
            )
        }
    }

    private fun onMarkAllAsReadClick() {
        _state.value = state.value.copy(
            alertItems = state.value.alertItems.toMutableMap().apply {
                forEach { (section, _) ->
                    compute(section) {_, list ->
                        list?.toMutableList()?.map { listItem -> listItem.copy(isRead = true) }
                    }
                }
            },
        )
    }
}
