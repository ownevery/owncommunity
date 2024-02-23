package com.gasparaiciukas.owncommunity.presentation.alertlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasparaiciukas.owncommunity.domain.alerts.model.AlertItem
import com.gasparaiciukas.owncommunity.domain.alerts.usecase.AlertListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AlertListViewModel @Inject constructor(
    private val alertListUseCase: AlertListUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AlertListState.EMPTY)
    val uiState = _uiState.asStateFlow()

    init {
        onCreated()
    }

    private fun onCreated() {
        val alerts = alertListUseCase.getAlerts()
        _uiState.update { state ->
            state.copy(alertMap = alerts)
        }
    }

    fun onEvent(event: AlertListState.Event) {
        when (event) {
            AlertListState.OnMarkAllAsReadClick -> onMarkAllAsReadClick()
            is AlertListState.OnAlertItemClick -> onAlertItemClick(event.item)
            AlertListState.NavigateToPostScreen -> {}
            AlertListState.NavigateToProfileScreen -> {}
        }
    }

    fun onEventHandled(event: AlertListState.Event?) {
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
                    state.alertMap.updateItem(
                        item.copy(
                            isRead = true,
                        ),
                    )
                val event =
                    if (item.type == AlertItem.Type.Birthday) {
                        AlertListState.NavigateToProfileScreen
                    } else {
                        AlertListState.NavigateToPostScreen
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
            val items = state.alertMap.updateAllItems { it.copy(isRead = true) }
            state.copy(
                alertMap = items,
            )
        }
    }

    private fun PersistentMap<AlertItem.Section, PersistentList<AlertItem>>.updateItem(
        alertItem: AlertItem
    ) = mutate { map ->
        map.compute(alertItem.section) { _, value ->
            value?.mutate { list ->
                val index = list.indexOfFirst { item -> item.id == alertItem.id }
                if (index != -1) {
                    list[index] = alertItem
                }
            }
        }
    }

    private fun PersistentMap<AlertItem.Section, PersistentList<AlertItem>>.updateAllItems(
        apply: (AlertItem) -> AlertItem
    ) = mutate { map ->
        this.forEach { (section, alertItems) ->
            val items =
                alertItems.mutate { list ->
                    list.replaceAll(apply)
                }
            map[section] = items
        }
    }
}
