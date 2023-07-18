package com.gasparaitis.owncommunity.presentation.alerts

import androidx.annotation.DrawableRes

data class AlertsState(
    val areAllAlertsRead: Boolean,
    val alertItems: List<AlertItem>,
) {
    companion object {
        val EMPTY = AlertsState(
            areAllAlertsRead = false,
            alertItems = listOf(),
        )
    }
}

data class AlertItem(
    val section: AlertItemSection,
    @DrawableRes val icon: Int,
    val title: String,
    val date: String,
) {
    companion object {
        val EMPTY = AlertItem(
            section = AlertItemSection.TODAY,
            icon = 0,
            title = "",
            date = ""
        )
    }
}

enum class AlertItemSection {
    TODAY,
    LAST_30_DAYS,
    OLDER,
}
