package com.gasparaitis.owncommunity.presentation.alerts

sealed class AlertsAction {
    object OnAlertItemClick : AlertsAction()
    object OnMarkAllAsReadClick : AlertsAction()
}
