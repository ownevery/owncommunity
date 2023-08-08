package com.gasparaitis.owncommunity.domain.alerts.model

import androidx.annotation.DrawableRes
import com.gasparaitis.owncommunity.R
import java.util.UUID

data class AlertItem(
    val id: String,
    val section: AlertItemSection,
    val type: AlertItemType,
    val title: String,
    val date: String,
    val isRead: Boolean,
) {
    companion object {
        val EMPTY = AlertItem(
            id = UUID.randomUUID().toString(),
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
