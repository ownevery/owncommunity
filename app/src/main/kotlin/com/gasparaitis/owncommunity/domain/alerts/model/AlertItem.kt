package com.gasparaitis.owncommunity.domain.alerts.model

import androidx.annotation.DrawableRes
import com.gasparaitis.owncommunity.R

data class AlertItem(
    val section: AlertItemSection,
    val type: AlertItemType,
    val title: String,
    val date: String,
    val isRead: Boolean,
) {
    companion object {
        val EMPTY = AlertItem(
            section = AlertItemSection.TODAY,
            type = AlertItemType.LIKE,
            title = "",
            date = "",
            isRead = false,
        )
    }
}

enum class AlertItemType(@DrawableRes val icon: Int) {
    LIKE(R.drawable.ic_alert_like),
    COMMENT(R.drawable.ic_alert_comment),
    BIRTHDAY(R.drawable.ic_alert_birthday),
    MENTION(R.drawable.ic_alert_mention)
}
