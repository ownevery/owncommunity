package com.gasparaitis.owncommunity.presentation.alerts

import com.gasparaitis.owncommunity.domain.alerts.model.AlertItem

sealed class AlertsAction {
    class OnAlertItemClick(val item: AlertItem) : AlertsAction()
    object OnMarkAllAsReadClick : AlertsAction()
}
