package com.gasparaitis.owncommunity.presentation.alerts

import com.gasparaitis.owncommunity.domain.alerts.model.AlertItem
import com.gasparaitis.owncommunity.domain.alerts.model.AlertItemSection

data class AlertsState(
    val alertItems: Map<AlertItemSection, List<AlertItem>>,
) {
    companion object {
        val EMPTY = AlertsState(
            alertItems = mapOf(),
        )
    }
}
